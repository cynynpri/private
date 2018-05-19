import numpy as np
import cv2
import threading
import pygame
import random
import os

#パターンマッチング方式のコンボSEパッチ

def preprocessing(img, name):
	#cv2.imwrite("raw_"+str(name)+"img.png", img)
	img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	img = cv2.GaussianBlur(img, (3, 3), 0)
	img = cv2.resize(img, (28, 28))
	res, img = cv2.threshold(img, 144, 255, cv2.THRESH_BINARY)
	img = 255 - img
	img = cv2.bitwise_not(img)
	#img = img.astype(np.float32)
	#cv2.imwrite(name, img)
	#img = np.array(img).reshape(1, 784)
	return img

def thread_music_play(combo):
	character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico"]
	file_selector = random.choice(character_name_list)
	pygame.mixer.init()
	if combo > 300:
		combo = None
		combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+file_selector+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)

def beforeprocessing():
	ichi_imgs = []
	zyuu_imgs = []
	hyaku_imgs = []
	paths = ["ichi", "zyuu", "hyaku"]
	for i in range(3):
		img_lists = os.listdir(paths[i])
		"""
		['ichi_0.png', 'ichi_10.png', 'ichi_100.png', 'ichi_200.png', 'ichi_300.png', 'ichi_400.png', 'ichi_401.png', 'ichi_50.png', 'ichi_500.png']
		['zyuu_0.png', 'zyuu_10.png', 'zyuu_100.png', 'zyuu_200.png', 'zyuu_300.png', 'zyuu_400.png', 'zyuu_50.png', 'zyuu_500.png', 'zyuu_9.png']
		['hyaku_1.png', 'hyaku_2.png', 'hyaku_3.png', 'hyaku_4.png', 'hyaku_400.png', 'hyaku_5.png', 'hyaku_500.png']
		"""
		lists_length = len(img_lists)
		for j in range(lists_length) :
			imgSrc = cv2.imread(paths[i]+"\\"+img_lists[j], 0)
			if imgSrc is None : continue
			if i == 0 :
				ichi_imgs.append(imgSrc)
			elif i == 1 :
				zyuu_imgs.append(imgSrc)
			elif i == 2 :
				hyaku_imgs.append(imgSrc)

	return ichi_imgs, zyuu_imgs, hyaku_imgs

ichi_imgs, zyuu_imgs, hyaku_imgs = beforeprocessing()


cap = cv2.VideoCapture(1)

combo = 0
analyzable = False
combo10 = False

playmusictimer = 0
playable = True
while (cap.isOpened()) :
	ret, frame = cap.read()
	if ret == True :
		if playable == False :
			playmusictimer += 1
			if playmusictimer >= 60 :
				playable = True
				playmusictimer = 0
		left = 730
		right = 960
		bottom = 415
		top = 501
		dif = 76
		hyaku_right = left+dif+1
		zyuu_right = hyaku_right+dif-1
		ichi_right = zyuu_right+dif+1
		cv2.imshow('LoveLive! School idol Festival', frame)
		k = cv2.waitKey(1)
		hyaku_img = frame[bottom:top, left:hyaku_right]
		zyuu_img = frame[bottom:top, hyaku_right:zyuu_right]
		ichi_img = frame[bottom:top, zyuu_right:ichi_right]
		ichi_img = preprocessing(ichi_img,"_")
		zyuu_img = preprocessing(zyuu_img, "_")
		hyaku_img = preprocessing(hyaku_img, "_")
		if k & 0xFF == ord('a'):
			analyzable = True
		if analyzable == True :
			if combo10 == False :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[1], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[1], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True :
					combo = 10
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
					combo10 = True
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.7 and max_val_z >= 0.7 and playable == True:
					combo = 10
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
					combo10 = True
			if combo == 10 :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[7], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True:
					combo = 50
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True:
					combo = 50
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
			elif combo == 50 :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[2], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[2], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[0], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 100
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.75 and max_val_z >= 0.75 and h_v_h >= 0.75 and playable == True:
					combo = 100
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
			elif combo == 100 :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[3], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[3], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[1], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 200
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.75 and max_val_z >= 0.75 and h_v_h >= 0.75 and playable == True:
					combo = 200
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
			elif combo == 200 :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[4], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[4], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[2], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 300
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.75 and max_val_z >= 0.75 and h_v_h >= 0.75 and playable == True:
					combo = 300
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
			elif combo == 300 :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[5], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[5], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[3], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 400
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.75 and max_val_z >= 0.75 and h_v_h >= 0.75 and playable == True:
					combo = 400
					playable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
			elif combo == 400 :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[8], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[7], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 500
					playable = False
					analyzable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.75 and max_val_z >= 0.75 and h_v_h >= 0.75 and playable == True:
					combo = 500
					playable = False
					analyzable = False
					m_s_thread = threading.Thread(target=thread_music_play(combo))
					m_s_thread.start()
			if k & 0xFF == ord('b'):
				analyzable = False
				combo = 0
		if k & 0xFF == ord('q') or k == 27:
			break
	else:
		break

cap.release()
cv2.destroyAllWindows()
