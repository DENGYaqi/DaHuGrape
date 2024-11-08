package com.example.dahugrape.ml1_cv.classification_image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.nnapi.NnApiDelegate;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileInputStream;

/**
 * 编写一个TFLiteClassificationUtil工具类，关于Tensorflow Lite的操作都在这里完成，如加载模型、预测。
 * 在构造方法中，通过参数传递的模型路径加载模型，在加载模型的时候配置预测信息，例如是否使用Android底层神经网络APINnApiDelegate或者是否使用GPUGpuDelegate，同时获取网络的输入输出层。
 * 有了tensorflow-lite-support库，数据预处理就变得非常简单，通过ImageProcessor创建一个数据预处理的工具，
 * 之后在预测之前使用这个工具对图像进行预处理，处理速度还是挺快的，要注意的是图像的均值IMAGE_MEAN和标准差IMAGE_STD，
 * 因为在训练的时候图像预处理可能不一样的，有些读者出现在电脑上准确率很高，但在手机上准确率很低，多数情况下就是这个图像预处理做得不对。
 */
public class TFLiteClassificationUtil {
    private static final String TAG = TFLiteClassificationUtil.class.getName();
    private static final int NUM_THREADS = 4;
    private static final float[] IMAGE_MEAN = new float[]{128.0f, 128.0f, 128.0f};
    private static final float[] IMAGE_STD = new float[]{128.0f, 128.0f, 128.0f};
    private final TensorBuffer outputProbabilityBuffer;
    private final ImageProcessor imageProcessor;
    private final Interpreter tflite;
    private TensorImage inputImageBuffer;


    /**
     * @param modelPath model path
     */
    public TFLiteClassificationUtil(String modelPath) throws Exception {


        File file = new File(modelPath);
        if (!file.exists()) {
            throw new Exception("model file is not exists!");
        }

        try {
            Interpreter.Options options = new Interpreter.Options();
            // 使用多线程预测
            options.setNumThreads(NUM_THREADS);
            // 使用Android自带的API或者GPU加速
            NnApiDelegate delegate = new NnApiDelegate();
//            GpuDelegate delegate = new GpuDelegate();
            options.addDelegate(delegate);
            tflite = new Interpreter(file, options);
            // 获取输入，shape为{1, height, width, 3}
            int[] imageShape = tflite.getInputTensor(tflite.getInputIndex("input_1")).shape();
            DataType imageDataType = tflite.getInputTensor(tflite.getInputIndex("input_1")).dataType();
            inputImageBuffer = new TensorImage(imageDataType);
            // 获取输入，shape为{1, NUM_CLASSES}
            int[] probabilityShape = tflite.getOutputTensor(tflite.getOutputIndex("Identity")).shape();
            DataType probabilityDataType = tflite.getOutputTensor(tflite.getOutputIndex("Identity")).dataType();
            outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);

            // 添加图像预处理方式
            imageProcessor = new ImageProcessor.Builder()
                    .add(new ResizeOp(imageShape[1], imageShape[2], ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                    .add(new NormalizeOp(IMAGE_MEAN, IMAGE_STD))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("load model fail!");
        }
    }

    // 获取概率最大的标签
    // 这里创建一个获取最大概率值，并把下标返回的方法，其实就是获取概率最大的预测标签。
    public static int getMaxResult(float[] result) {
        float probability = 0;
        int r = 0;
        for (int i = 0; i < result.length; i++) {
            if (probability < result[i]) {
                probability = result[i];
                r = i;
            }
        }
        return r;
    }

    // 重载方法，根据图片路径转Bitmap预测
    // 为了兼容图片路径和Bitmap格式的图片预测，这里创建了两个重载方法，它们都是通过调用predict()
    public float[] predictImage(String image_path) throws Exception {
        if (!new File(image_path).exists()) {
            throw new Exception("image file is not exists!");
        }
        FileInputStream fis = new FileInputStream(image_path);
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        float[] result = predictImage(bitmap);
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return result;
    }

    // 重载方法，直接使用Bitmap预测
    public float[] predictImage(Bitmap bitmap) throws Exception {
        return predict(bitmap);
    }

    // 数据预处理
    private TensorImage loadImage(final Bitmap bitmap) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);
        return imageProcessor.process(inputImageBuffer);
    }

    // 执行预测
    // 这个方法就是Tensorflow Lite执行预测的最后一步，通过执行tflite.run()对输入的数据进行预测并得到预测结果，
    // 通过解析获取到最大的概率的预测标签，并返回。到这里Tensorflow Lite的工具就完成了。
    private float[] predict(Bitmap bmp) throws Exception {
        inputImageBuffer = loadImage(bmp);

        try {
            tflite.run(inputImageBuffer.getBuffer(), outputProbabilityBuffer.getBuffer().rewind());
        } catch (Exception e) {
            throw new Exception("predict image fail! log:" + e);
        }
        float[] results = outputProbabilityBuffer.getFloatArray();
        int l = getMaxResult(results);
        return new float[]{l, results[l]};
    }
}
