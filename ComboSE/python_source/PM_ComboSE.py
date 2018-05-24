import numpy as np
import cv2
import threading
import pygame
import random
import os
import concurrent.futures
import pyperclip
from datetime import datetime, timedelta
#import sys

#making csharp version ComboSE.py
"""
C#版作成用メモ
参考:
http://www.moonmile.net/blog/archives/6258
↑コード参照元

http://ufcpp.net/study/csharp/sp_thread.html
↑マルチスレッド化

https://shimat.github.io/opencvsharp_docs/html/bafd2970-4ef9-ddb6-a280-146f7ab1388b.htm
↑CVSharpのドキュメント(公式?)

"""

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

def thread_music_play(combo, muse=True, aqours=False):
	if muse == True and aqours == True:
		all_character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico", "chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"]
	elif muse == True :
		character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico"]
	elif aqours == True :
		aq_character_name_list = ["chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"]
	if muse == True and aqours == True:
		file_selector = random.choice(all_character_name_list)
	elif muse == True :
		file_selector = random.choice(character_name_list)
	elif aqours == True:
		file_selector = random.choice(aq_character_name_list)
	pygame.mixer.init()
	if combo > 300:
		combo = None; combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+file_selector+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)

def beforeprocessing():
	ichi_imgs = []; zyuu_imgs = []; hyaku_imgs = []
	character_face_imgs = []
	paths = ["ichi", "zyuu", "hyaku", "faces"]
	for i in range(4):
		img_lists = os.listdir(paths[i])
		#print(img_lists)
		"""
		['ichi_0.png', 'ichi_10.png', 'ichi_100.png', 'ichi_200.png', 'ichi_300.png', 'ichi_400.png', 'ichi_401.png', 'ichi_50.png', 'ichi_500.png']
		['zyuu_0.png', 'zyuu_10.png', 'zyuu_100.png', 'zyuu_200.png', 'zyuu_300.png', 'zyuu_400.png', 'zyuu_50.png', 'zyuu_500.png', 'zyuu_9.png']
		['hyaku_1.png', 'hyaku_2.png', 'hyaku_3.png', 'hyaku_4.png', 'hyaku_400.png', 'hyaku_5.png', 'hyaku_500.png']
		['chika.png', 'hanayo.png', 'kotori.png', 'maru.png', 'riko.png', 'riko_1.png', 'rin.png', 'umi_1.png', 'umi_2.png', 'yoshiko.png', 'you.png']
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
			elif i == 3 :
				character_face_imgs.append(imgSrc)

	return ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs

def mod_music_play(combo, name):
	if name == "_":
		print("Error!!:character name is illiegal value.")
		print("name is "+ str(name))
		thread_music_play(combo)
		return
	if name != "honoka" and name != "eli" and name != "kotori" and name != "umi" and name != "rin" and name != "maki" and name != "nozomi" and name != "hanayo" and name != "nico" and name != "chika" and name != "riko" and name != "kanan" and name != "dia" and name != "you" and name != "yoshiko" and name != "hanamaru" and name != "mari" and name != "ruby" and name != "_":
		print("Error!!:Unknown character name Error!!")
		print("name is"+str(name))
		thread_music_play(combo)
		return
	pygame.mixer.init()
	if combo > 300:
		combo = None; combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+name+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)

#==============================================================================================================================================================================================================================
#main_process key: "a" -> start analyze "b" -> end analyze "s" -> aqours mode. "d" -> all_star mode. "z" -> detect mode. "q" -> quit process. "u" -> umi mode. "r" -> riko mode. "x" -> difficulty changeing.(EX->TEC TEC->EX)
#==============================================================================================================================================================================================================================
debug = False

clip = ""
if pyperclip.is_available():
	clip = pyperclip.paste()

"""
available_URL = False
argvs = sys.argv
if len(argvs) == 2 :
	available_URL = True;url = argvs[1]
"""

code_checkable = False #if code_checkable = true do line 129 to comment in.

ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs = beforeprocessing()

if not code_checkable :
	cap = cv2.VideoCapture(0)

analyzable = False; combo = 0; combo10 = False
combo50 = False; combo100 = False; combo200 = False
combo300 = False; combo400 = False; combo500 = False
comboend = False
muse = True; aqours = False
playmusictimer = 0; playable = True
difficulty = "EX"
rating_mode = False

resetcounter = 0; resetcounting = 0
character_detectable = False
detectabletimer = 0; detected = False; name = "_"

left = 730; right = 960; top = 415; bottom = 501; dif = 76
hyaku_right = left+dif+1; zyuu_right = hyaku_right+dif-1; ichi_right = zyuu_right+dif+1
faceleft = 855; faceright = 1067; facetop = 820; facebottom = 1025

first_time = True

#"""
while (cap.isOpened()) :
	ret, frame = cap.read()
	if ret == True :
		if resetcounter > 0 :
			resetcounting += 1
			if resetcounting >= 600 :
				resetcounter = 0; resetcounting = 0
		if playable == False :
			playmusictimer += 1
			if playmusictimer >= 60 :
				playable = True; playmusictimer = 0
		"""
		if analyzable == True :
			cv2.rectangle(frame, (hyaku_right,bottom),(left,top),(0,0,255),3)
			cv2.rectangle(frame, (zyuu_right, bottom), (hyaku_right, top), (0, 255, 0), 3)
			cv2.rectangle(frame, (ichi_right, bottom), (zyuu_right, top), (255, 0, 0), 3)
		"""
		cv2.imshow('LoveLive! School idol Festival', frame)
		k = cv2.waitKey(1)
		if difficulty == "TEC" :
			rating_mode = True
		if k & 0xFF == ord('a'):
			analyzable = True
		if k & 0xFF == ord('s'):
			muse = False; aqours = True
		if k & 0xFF == ord('d'):
			muse = True; aqours = True
		if k & 0xFF == ord('z'):
			character_detectable = True
		if k & 0xFF == ord('u'):
			name = "umi"; detected = True
		if k & 0xFF == ord('r'):
			name = "riko"; detected = True
		if debug == True :
			hyaku_img = frame[top:bottom, left:hyaku_right]
			zyuu_img = frame[top:bottom, hyaku_right:zyuu_right]
			ichi_img = frame[top:bottom, zyuu_right:ichi_right]
			face_img = frame[facetop:facebottom, faceleft:faceright]
			hyaku_img = preprocessing(hyaku_img, "_")
			zyuu_img = preprocessing(zyuu_img, "_")
			face_img = preprocessing(face_img, "_")
			ichi_img = preprocessing(ichi_img, "_")
		if analyzable == True :
			hyaku_img = frame[top:bottom, left:hyaku_right]
			zyuu_img = frame[top:bottom, hyaku_right:zyuu_right]
			if combo10 == False or combo50 == False :
				ichi_img = frame[top:bottom, zyuu_right:ichi_right]
				ichi_img = preprocessing(ichi_img, "_")
			zyuu_img = preprocessing(zyuu_img, "_")
			hyaku_img = preprocessing(hyaku_img, "_")
			if character_detectable == True :
				face_img = frame[facetop:facebottom, faceleft:faceright]
				face_img = preprocessing(face_img, "_")
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[0], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "chika"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[1], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "hanayo"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[2], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "kotori"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[3], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "hanamaru"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[4], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "riko"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[5], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "riko"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[6], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "rin"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[7], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "umi"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[8], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "umi"; character_detectable = False; detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[9], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "yoshiko";character_detectable = False;detected = True
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[10], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8:
					name = "you"; character_detectable = False; detected = True
				detectabletimer += 1
			if detectabletimer >= 120 :
				character_detectable = False; detectabletimer = 0
			if combo10 == False :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[1], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[1], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True :
					combo = 10; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo10 = True
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.7 and max_val_z >= 0.7 and playable == True:
					combo = 10; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo10 = True
			if combo50 == False :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[7], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True:
					combo = 50; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo50 = True
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True:
					combo = 50; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo50 = True
			if combo100 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[2], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[0], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 100; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo100 = True
			if combo200 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[3], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[1], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 200; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo200 = True
			if combo300 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[4], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[2], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 300; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo300 = True
			if combo400 == False:
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[5], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[3], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 400; playable = False
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo400 = True
			if combo500 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[7], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
					combo = 500; playable = False; comboend = True
					if detected == True and name != "_":
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else:
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo500 = True
			if k & 0xFF == ord('b') or comboend == True:
				#booleans initializer
				analyzable = False; combo = 0; combo10 = False
				combo50 = False; combo100 = False; combo200 = False
				combo300 = False; combo400 = False; combo500 = False
				comboend = False
				resetcounter += 1
				if character_detectable == True: 
					character_detectable = False; name = "_"
				if detected == True :
					character_detectable = False; name = "_"; detected = False
				if name != "_":
					name = "_"
				if resetcounter >= 5 :
					muse = True; aqours = False; resetcounter = 0
				dt = datetime.now()
				dt = dt + timedelta(minutes=1) + timedelta(seconds=32)
				time = dt.strftime("%M:%S")
				clip_temp = difficulty + " " + time
				pyperclip.copy(clip_temp)
			if k & 0xFF == ord('v') :
				muse = True; aqours = False
		if k & 0xFF == ord('x') :
			if difficulty == "EX" :
				difficulty = "TEC"
			elif difficulty == "TEC" :
				difficulty = "EX"
		if k & 0xFF == ord('b') :
			if first_time :
				dt = datetime.now()
				dt = dt + timedelta(seconds=45)
				first_time = False
			else :
				dt = datetime.now()
				dt = dt + timedelta(minutes=1)+ timedelta(seconds=10)
			time = dt.strftime("%M:%S")
			clip_temp = difficulty + " " + time
			pyperclip.copy(clip_temp)
		if k & 0xFF == ord('q') or k & 0xFF == 27:
			if debug == True :
				cv2.imwrite("face.png",face_img)
			break
	else:
		break

cap.release()
cv2.destroyAllWindows()
#"""
pyperclip.copy(clip)
