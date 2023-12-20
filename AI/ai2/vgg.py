import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()
# import tensorflow as tf
import numpy as np
import scipy.io
import cv2
import scipy.misc

HEIGHT = 300
WIGHT = 450
LEARNING_RATE = 1.0
NOISE = 0.5
ALPHA = 1
BETA = 500

TRAIN_STEPS = 200

OUTPUT_IMAGE = "E:/vgg/python/img"
STYLE_LAUERS = [('conv1_1', 0.2), ('conv2_1', 0.2), ('conv3_1', 0.2), ('conv4_1', 0.2), ('conv5_1', 0.2)]
CONTENT_LAYERS = [('conv4_2', 0.5), ('conv5_2',0.5)]


def vgg19():
    layers=(
        'conv1_1','relu1_1','conv1_2','relu1_2','pool1',
        'conv2_1','relu2_1','conv2_2','relu2_2','pool2',
        'conv3_1','relu3_1','conv3_2','relu3_2','conv3_3','relu3_3','conv3_4','relu3_4','pool3',
        'conv4_1','relu4_1','conv4_2','relu4_2','conv4_3','relu4_3','conv4_4','relu4_4','pool4',
        'conv5_1','relu5_1','conv5_2','relu5_2','conv5_3','relu5_3','conv5_4','relu5_4','pool5'
    )
    vgg = scipy.io.loadmat('E:/imagenet-vgg-verydeep-19.mat')
    weights = vgg['layers'][0]

    network={}
    net = tf.Variable(np.zeros([1, 300, 450, 3]), dtype=tf.float32)
    network['input'] = net
    for i,name in enumerate(layers):
        layer_type=name[:4]
        if layer_type=='conv':
            kernels = weights[i][0][0][0][0][0]
            bias = weights[i][0][0][0][0][1]
            conv=tf.nn.conv2d(net,tf.constant(kernels),strides=(1,1,1,1),padding='SAME',name=name)
            net=tf.nn.relu(conv + bias)
        elif layer_type=='pool':
            net=tf.nn.max_pool(net,ksize=(1,2,2,1),strides=(1,2,2,1),padding='SAME')
        network[name]=net
    return network


# 求gamm矩阵
def gram(x, size, deep):
    x = tf.reshape(x, (size, deep))
    g = tf.matmul(tf.transpose(x), x)
    return g


def style_loss(sess, style_neck, model):
    style_loss = 0.0
    for layer_name, weight in STYLE_LAUERS:
        # 计算特征矩阵
        a = style_neck[layer_name]
        x = model[layer_name]
        # 长x宽
        M = a.shape[1] * a.shape[2]
        N = a.shape[3]

        # 计算gram矩阵
        A = gram(a, M, N)
        G = gram(x, M, N)

        # 根据公式计算损失，并进行累加
        style_loss += (1.0 / (4 * M * M * N * N)) * tf.reduce_sum(tf.pow(G - A, 2)) * weight
        # 将损失对层数取平均
    style_loss /= len(STYLE_LAUERS)
    return style_loss


def content_loss(sess, content_neck, model):
    content_loss = 0.0
    # 逐个取出衡量内容损失的vgg层名称及对应权重

    for layer_name, weight in CONTENT_LAYERS:
        # 计算特征矩阵
        p = content_neck[layer_name]
        x = model[layer_name]
        # 长x宽xchannel

        M = p.shape[1] * p.shape[2]
        N = p.shape[3]

        lss = 1.0 / (M * N)
        content_loss += lss * tf.reduce_sum(tf.pow(p - x, 2)) * weight
        # 根据公式计算损失，并进行累加

    # 将损失对层数取平均
    content_loss /= len(CONTENT_LAYERS)
    return content_loss


def random_img(height, weight, content_img):
    noise_image = np.random.uniform(-20, 20, [1, height, weight, 3])
    random_img = noise_image * NOISE + content_img * (1 - NOISE)
    return random_img


def get_neck(sess, model, content_img, style_img):
    sess.run(tf.compat.v1.assign(model['input'], content_img))
    # sess.run(tf.assign(model['input'], content_img))
    # Tensorflow 2.0版本中已经移除了Session这一模块
    content_neck = {}
    for layer_name, weight in CONTENT_LAYERS:
        # 计算特征矩阵
        p = sess.run(model[layer_name])
        content_neck[layer_name] = p
    sess.run(tf.compat.v1.assign(model['input'], style_img))
    # sess.run(tf.assign(model['input'], style_img))
    style_content = {}
    for layer_name, weight in STYLE_LAUERS:
        # 计算特征矩阵
        a = sess.run(model[layer_name])
        style_content[layer_name] = a
    return content_neck, style_content


def main():
    model = vgg19()
    content_img = cv2.imread('E:/vgg/img/rainier3.jpg')
    content_img = cv2.resize(content_img, (450, 300))
    content_img = np.reshape(content_img, (1, 300, 450, 3)) - [128.0, 128.2, 128.0]
    style_img = cv2.imread('E:/vgg/img/autumn-oak.jpg')
    style_img = cv2.resize(style_img, (450, 300))
    style_img = np.reshape(style_img, (1, 300, 450, 3)) - [128.0, 128.2, 128.0]

    # 生成图片
    rand_img = random_img(HEIGHT, WIGHT, content_img)


    tf.compat.v1.disable_eager_execution()
    with tf.compat.v1.Session() as sess:
        # 计算loss值
        content_neck, style_neck = get_neck(sess, model, content_img, style_img)
        cost = ALPHA * content_loss(sess, content_neck, model) + BETA * style_loss(sess, style_neck, model)
        optimizer = tf.train.AdamOptimizer(LEARNING_RATE).minimize(cost)

        sess.run(tf.global_variables_initializer())
        sess.run(tf.assign(model['input'], rand_img))
        for step in range(TRAIN_STEPS):
            print(step)
            # 训练
            sess.run(optimizer)

            if step % 10 == 0:
                img = sess.run(model['input'])
                img += [128, 128, 128]
                img = np.clip(img, 0, 255).astype(np.uint8)
                name = OUTPUT_IMAGE + "//" + str(step) + ".jpg"
                img = img[0]
                cv2.imwrite(name, img)

        img = sess.run(model['input'])
        img += [128, 128, 128]
        img = np.clip(img, 0, 255).astype(np.uint8)
        cv2.imwrite("E:/vgg/end.jpg", img[0])

main()
