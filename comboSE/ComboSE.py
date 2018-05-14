import numpy as np
import cv2
import chainer
from chainer import Chain, serializers, Variable
import chainer.functions as F
import chainer.links as L
import threading
import pygame
import random

#https://qiita.com/EN-0/items/784b919ea0c090871a08
#https://sites.google.com/site/faxocr2010/systemrequirements/kocr/mnist
class MyMLP(chainer.Chain):
	def __init__(self, n_in = 784, n_units = 500, n_out = 10):
		super(MyMLP, self).__init__(
			l1=L.Linear(n_in, n_units),
			l2=L.Linear(n_units, n_units),
			l3=L.Linear(n_units, n_out),
		)
	def __call__(self, x):
		h1 = F.relu(self.l1(x))
		h2 = F.relu(self.l2(h1))
		return self.l3(h2)

def preprocessing(img, name):
	#cv2.imwrite("raw_"+str(name)+"img.png", img)
	img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	img = cv2.GaussianBlur(img, (3, 3), 0)
	img = cv2.resize(img, ( 28, 28))
	res, img = cv2.threshold(img, 144, 255, cv2.THRESH_BINARY)
	img = 255 - img
	img = cv2.bitwise_not(img)
	img = img.astype(np.float32)
	#cv2.imwrite(name, img)
	img = np.array(img).reshape(1,784)
	return img

def check_accuracy(net, xs, ts):
	ys = net(xs)
	loss = F.softmax_cross_entropy(ys, ts)
	ys = np.argmax(ys.data, axis=1)
	cors = (ys == ts)
	num_cors = sum(cors)
	accuracy = num_cors / ts.shape[0]
	return accuracy, loss

def thread_music_play(combo):
	character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico"]
	file_selector = random.choice(character_name_list)
	pygame.mixer.init()
	if combo > 300 :
		combo = None
		combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+file_selector+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)
	return

"""
#学習データ出力用main文
net = MyMLP()
#serializers.load_npz('mymodel.npz', net)
optimizer = chainer.optimizers.Adam()
optimizer.setup(net)

train, test = chainer.datasets.get_mnist()
#train, test = load_faxocr_dataset()
xs, ts = train._datasets
txs, tts = test._datasets

bm = 100
#https://qiita.com/mitmul/items/1e35fba085eb07a92560
for i in range(8):
	for j in range(600):
		net.zerograds()
		x = xs[(j * bm):((j+1)*bm)]
		t = ts[(j * bm):((j+1)*bm)]
		t = Variable(np.array(t, "i"))
		y = net(x)
		loss = F.softmax_cross_entropy(y, t)
		loss.backward()
		optimizer.update()
	
	accuracy_train, loss_train = check_accuracy(net, xs, ts)
	accuracy_test, _ = check_accuracy(net,txs, tts)

	print("Epoch %d loss(train) = %f, accuracy(train) = %f, accuracy(test) = %f" % (i + 1, loss_train.data, accuracy_train, accuracy_test))

serializers.save_npz("mymodel.npz", net)

"""

net = MyMLP()
serializers.load_npz('mymodel.npz', net)
cap = cv2.VideoCapture(1)
#0 ???
#1 drecap
#2 gv usb?
#3 Web camera

combo = 0
analyzable = False
more_than_ten = False
more_than_handred = False

while(cap.isOpened()):
	ret, frame = cap.read()
	if ret == True:
		left = 730
		right = 960
		bottom = 415
		top = 501
		dif = 76
		hyaku_right = left+dif+1
		zyuu_right = hyaku_right+dif-1
		ichi_right = zyuu_right+dif+1
		#cv2.rectangle(frame, (hyaku_right,bottom),(left,top),(0,0,255),3)
		#cv2.rectangle(frame, (zyuu_right, bottom), (hyaku_right, top), (0, 255, 0), 3)
		#cv2.rectangle(frame, (ichi_right, bottom), (zyuu_right, top), (255, 0, 0), 3)
		cv2.imshow('LoveLive! School idor Festival', frame)
		k = cv2.waitKey(1)
		hyaku_img = frame[bottom:top,left:hyaku_right]
		zyuu_img = frame[bottom:top, hyaku_right:zyuu_right]
		ichi_img = frame[bottom:top, zyuu_right:ichi_right]

		if cv2.waitKey(1) & 0xFF == ord('a') :
			analyzable = True

		if analyzable == True :
			ichi = preprocessing(ichi_img, "ichi.png")
			ichi_num = net(ichi)
			zyuu = preprocessing(zyuu_img, "zyuu.png")
			zyuu_num = net(zyuu)
			hyaku = preprocessing(hyaku_img, "hyaku.png")
			hyaku_num = net(hyaku)
			combo = int(np.argmax(hyaku_num.data))*100 + int(np.argmax(zyuu_num.data)) * 10 + int(np.argmax(ichi_num.data))

			#print(str(hyaku_num.data))
			#print(str(np.argmax(hyaku_num.data))+str(np.argmax(zyuu_num.data))+str(np.argmax(ichi_num.data)))
			
			#combo = int(np.argmax(hyaku_num.data))*100 + int(np.argmax(zyuu_num.data)) * 10 + int(np.argmax(ichi_num.data))
			print("combo is "+str(combo))
			if combo == 10 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if combo == 50 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if combo == 100 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if combo == 200 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if combo == 300 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if combo == 400 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if combo == 500 :
				music_start_thread = threading.Thread(target=thread_music_play(combo))
				music_start_thread.start()
			if cv2.waitKey(1) & 0xFF == ord('b'):
				analyzable = False
				more_than_ten = False
				more_than_handred = False
				combo = 0
		if cv2.waitKey(1) & 0xFF == ord('q') or k == 27:
			break
	else:
		break

cap.release()
cv2.destroyAllWindows()
