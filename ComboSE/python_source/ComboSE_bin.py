import numpy as np
import cv2
import threading
import pygame
import random
import os
import concurrent.futures
import pyperclip
from datetime import datetime, timedelta
import sys

#making csharp version ComboSE.py
#exe化するpythonコード.

def preprocessing(img, name, debug=False):
	if name == "_" :
		debug = False
	if debug :
		cv2.imwrite(name+"_raw.png", img)
	img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	img = cv2.GaussianBlur(img, (3, 3), 0)
	img = cv2.resize(img, (28, 28))
	res, img = cv2.threshold(img, 144, 255, cv2.THRESH_BINARY)
	img = 255 - img
	img = cv2.bitwise_not(img)
	if debug :
		cv2.imwrite(name+".png", img)
	return img


def thread_music_play(combo, muse=True, aqours=False):
	if muse == True and aqours == True:
		all_character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico", "chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"]
	elif muse == True:
		character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico"]
	elif aqours == True:
		aq_character_name_list = ["chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"]
	if muse == True and aqours == True:
		file_selector = random.choice(all_character_name_list)
	elif muse == True:
		file_selector = random.choice(character_name_list)
	elif aqours == True:
		file_selector = random.choice(aq_character_name_list)
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
	character_face_imgs = []
	u_file_names = []
	t_file_names = []
	h_file_names = []
	chara_file_names = []
	paths = ["units_nums", "tens", "hundreds", "faces"]
	for i in range(4):
		img_lists = os.listdir(paths[i])
		lists_length = len(img_lists)
		for j in range(lists_length):
			imgSrc = cv2.imread(paths[i]+"\\"+img_lists[j], 0)
			if imgSrc is None:
				continue
			if i == 0:
				ichi_imgs.append(imgSrc)
				u_file_names.append(img_lists[j])
			elif i == 1:
				zyuu_imgs.append(imgSrc)
				t_file_names.append(img_lists[j])
			elif i == 2:
				hyaku_imgs.append(imgSrc)
				h_file_names.append(img_lists[j])
			elif i == 3:
				character_face_imgs.append(imgSrc)
				chara_file_names.append(img_lists[j])
	for i in range(len(u_file_names)):
		t = ichi_imgs[i]
		p = u_file_names[i]
		if p in "10" and not p in "100" and i != 0 :
			m = ichi_imgs[0]
			ichi_imgs.insert(0, t)
			ichi_imgs.insert(i, m)
		if p in "50" and not p in "500" and i != 1 :
			m = ichi_imgs[1]
			ichi_imgs.insert(1, t)
			ichi_imgs.insert(i, m)

	for i in range(len(t_file_names)) :
		t = zyuu_imgs[i]
		p = t_file_names[i]
		if p in "10" and not p in "100" and i != 0 :
			m = zyuu_imgs[0]
			zyuu_imgs.insert(0,t)
			zyuu_imgs.insert(i,m)
		if p in "50" and not p in "500" and i != 1 :
			m = zyuu_imgs[1]
			zyuu_imgs.insert(1,t)
			zyuu_imgs.insert(i,m)
		if p in "100" and i != 2 :
			m = zyuu_imgs[2]
			zyuu_imgs.insert(2,t)
			zyuu_imgs.insert(i,m)
		if p in "200" and i != 3 :
			m = zyuu_imgs[3]
			zyuu_imgs.insert(3,t)
			zyuu_imgs.insert(i,m)
		if p in "300" and i != 4 :
			m = zyuu_imgs[4]
			zyuu_imgs.insert(4,t)
			zyuu_imgs.insert(i,m)
		if p in "400" and i != 5 :
			m = zyuu_imgs[5]
			zyuu_imgs.insert(5,t)
			zyuu_imgs.insert(i,m)
		if p in "500" and i != 6 :
			m = zyuu_imgs[6]
			zyuu_imgs.insert(6,t)
			zyuu_imgs.insert(i,m)

	for i in range(len(h_file_names)):
		t = hyaku_imgs[i]
		p = h_file_names[i]
		if p in "100" and i != 0:
			m = hyaku_imgs[0]
			hyaku_imgs.insert(0, t)
			hyaku_imgs.insert(i, m)
		if p in "200" and i != 1 :
			m = hyaku_imgs[1]
			hyaku_imgs.insert(1, t)
			hyaku_imgs.insert(i,m)
		if p in "300" and i != 2:
			m = hyaku_imgs[2]
			hyaku_imgs.insert(2, t)
			hyaku_imgs.insert(i, m)
		if p in "400" and i != 3:
			m = hyaku_imgs[3]
			hyaku_imgs.insert(3, t)
			hyaku_imgs.insert(i, m)
		if p in "500" and i != 4:
			m = hyaku_imgs[4]
			hyaku_imgs.insert(4, t)
			hyaku_imgs.insert(i, m)
	return ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs, chara_file_names


def mod_music_play(combo, name):
	if name == "_":
		print("Error!!:character name is illiegal value.")
		print("name is " + str(name))
		thread_music_play(combo)
		return
	if name != "honoka" and name != "eli" and name != "kotori" and name != "umi" and name != "rin" and name != "maki" and name != "nozomi" and name != "hanayo" and name != "nico" and name != "chika" and name != "riko" and name != "kanan" and name != "dia" and name != "you" and name != "yoshiko" and name != "hanamaru" and name != "mari" and name != "ruby" and name != "_":
		print("Error!!:Unknown character name Error!!")
		print("name is"+str(name))
		thread_music_play(combo)
		return
	pygame.mixer.init()
	if combo > 300:
		combo = None
		combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+name+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)

#==============================================================================================================================================================================================================================
#main_process key: "a" -> start analyze "b" -> end analyze "s" -> aqours mode. "d" -> all_star mode. "z" -> detect mode. "q" -> quit process. "u" -> umi mode. "r" -> riko mode. "x" -> difficulty changeing.(EX->TEC TEC->EX)
#==============================================================================================================================================================================================================================
def main_process(device_index, Combo_img_x, Combo_img_y, Combo_digits_width_numud, Combo_digits_height_numud, face_x_numud, face_y_numud, face_width_numud, face_height_numud, device_detecting, picture_Takeble, cutting_showable, div, chara_choosable):

	debug = False
	div = div/100.0

	clip = ""
	if pyperclip.is_available():
		clip = pyperclip.paste()

	ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs, chara_file_names = beforeprocessing()

	cap = cv2.VideoCapture(device_index)

	analyzable = False
	combo = 0
	combo10 = False
	combo50 = False
	combo100 = False
	combo200 = False
	combo300 = False
	combo400 = False
	combo500 = False
	comboend = False
	muse = True
	aqours = False
	playmusictimer = 0
	playable = True
	difficulty = "EX"
	rating_mode = False

	resetcounter = 0
	resetcounting = 0
	character_detectable = False
	detectabletimer = 0
	detected = False
	name = "_"

	left = Combo_img_x
	right = Combo_img_x + Combo_digits_width_numud
	top = Combo_img_y
	bottom = Combo_img_y + Combo_digits_height_numud
	dif = Combo_digits_width_numud
	hyaku_right = left+dif+1
	zyuu_right = hyaku_right+dif-1
	ichi_right = zyuu_right+dif+1
	faceleft = face_x_numud
	faceright = face_x_numud + face_width_numud
	facetop = face_y_numud
	facebottom = face_y_numud + face_height_numud

	first_time = True

	while (cap.isOpened()):
		ret, frame = cap.read()
		if ret == True:
			if resetcounter > 0:
				resetcounting += 1
				if resetcounting >= 600:
					resetcounter = 0
					resetcounting = 0
			if playable == False:
				playmusictimer += 1
				if playmusictimer >= 60:
					playable = True
					playmusictimer = 0
			if device_detecting == 0 :
				if analyzable == True and cutting_showable == 1:
					cv2.rectangle(frame, (hyaku_right,bottom),(left,top),(0,0,255),3)
					cv2.rectangle(frame, (zyuu_right, bottom), (hyaku_right, top), (0, 255, 0), 3)
					cv2.rectangle(frame, (ichi_right, bottom), (zyuu_right, top), (255, 0, 0), 3)
			cv2.imshow('LoveLive! School Idol Festival Display.exe', frame)
			k = cv2.waitKey(1)
			if difficulty == "TEC":
				rating_mode = True
			if k & 0xFF == ord('a') and device_detecting == 0:
				analyzable = True
			if k & 0xFF == ord('s'):
				muse = False
				aqours = True
			if k & 0xFF == ord('d'):
				muse = True
				aqours = True
			if k & 0xFF == ord('z'):
				character_detectable = True
			if chara_choosable == 1 and not detected :
				character_detectable = True
			if k & 0xFF == ord('u'):
				name = "umi"
				detected = True
			if k & 0xFF == ord('r'):
				name = "riko"
				detected = True
			if analyzable == True and device_detecting == 0:
				hyaku_img = frame[top:bottom, left:hyaku_right]
				zyuu_img = frame[top:bottom, hyaku_right:zyuu_right]
				if combo10 == False or combo50 == False:
					ichi_img = frame[top:bottom, zyuu_right:ichi_right]
					ichi_img = preprocessing(ichi_img, "_")
				zyuu_img = preprocessing(zyuu_img, "_")
				hyaku_img = preprocessing(hyaku_img, "_")

				if character_detectable == True :
					face_img = frame[facetop:facebottom, faceleft:faceright]
					face_img = preprocessing(face_img, "_")
					file_index = 0
					for t in character_face_imgs :
						res_faces = cv2.matchTemplate(face_img, t, cv2.TM_CCOEFF_NORMED)
						l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
						if h_v_f >= div :
							chara_name = chara_file_names[file_index]
							print(chara_name)
							if "honoka" in chara_name :
								name = "honoka"
								detected = True
								character_detectable = False
							elif "eli" in chara_name :
								name = "eli"
								detected = True
								character_detectable = False
							elif "kotori"in chara_name :
								name = "kotori"
								detected = True
								character_detectable = False
							elif "umi"in chara_name :
								name = "umi"
								detected = True
								character_detectable = False
							elif "rin"in chara_name :
								name = "rin"
								detected = True
								character_detectable = False
							elif "maki"in chara_name :
								name = "maki"
								detected = True
								character_detectable = False
							elif "nozomi"in chara_name :
								name = "nozomi"
								detected = True
								character_detectable = False
							elif "hanayo"in chara_name :
								name = "hanyo"
								detected = True
								character_detectable = False
							elif "nico"in chara_name :
								name = "nico"
								detected = True
								character_detectable = False
							elif "chika"in chara_name :
								name = "chika"
								detected = True
								character_detectable = False
							elif "riko"in chara_name :
								name = "riko"
								detected = True
								character_detectable = False
							elif "kanan"in chara_name :
								name = "kanan"
								detected = True
								character_detectable = False
							elif "dia"in chara_name :
								name = "dia"
								detected = True
								character_detectable = False
							elif "you"in chara_name :
								name = "you"
								detected = True
								character_detectable = False
							elif "yoshiko"in chara_name :
								name = "yoshiko"
								detected = True
								character_detectable = False
							elif "hanamaru"in chara_name :
								name = "hanamaru"
								detected = True
								character_detectable = False
							elif "mari"in chara_name:
								name = "mari"
								detected = True
								character_detectable = False
							elif "ruby"in chara_name :
								name = "ruby"
								detected = True
								character_detectable = False
						file_index += 1
					detectabletimer += 1
				if detectabletimer >= 120:
					character_detectable = False
					detectabletimer = 0
					file_index = 0
				if combo10 == False:
					result_ichi = cv2.matchTemplate(
						ichi_img, ichi_imgs[0], cv2.TM_CCOEFF_NORMED)
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[0], cv2.TM_CCOEFF_NORMED)
					min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					if max_val_i >= div and max_val_z >= div and playable == True:
						combo = 10
						playable = False
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo10 = True
				if combo50 == False:
					result_ichi = cv2.matchTemplate(
						ichi_img, ichi_imgs[1], cv2.TM_CCOEFF_NORMED)
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[1], cv2.TM_CCOEFF_NORMED)
					min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					if max_val_i >= div and max_val_z >= div and playable == True:
						combo = 50
						playable = False
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo50 = True
				if combo100 == False:
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[2], cv2.TM_CCOEFF_NORMED)
					result_hyaku = cv2.matchTemplate(
						hyaku_img, hyaku_imgs[0], cv2.TM_CCOEFF_NORMED)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
					if max_val_z >= div and h_v_h >= div and playable == True:
						combo = 100
						playable = False
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo100 = True
				if combo200 == False:
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[3], cv2.TM_CCOEFF_NORMED)
					result_hyaku = cv2.matchTemplate(
						hyaku_img, hyaku_imgs[1], cv2.TM_CCOEFF_NORMED)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
					if max_val_z >= div and h_v_h >= div and playable == True:
						combo = 200
						playable = False
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo200 = True
				if combo300 == False:
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[4], cv2.TM_CCOEFF_NORMED)
					result_hyaku = cv2.matchTemplate(
						hyaku_img, hyaku_imgs[2], cv2.TM_CCOEFF_NORMED)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
					if max_val_z >= div and h_v_h >= div and playable == True:
						combo = 300
						playable = False
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo300 = True
				if combo400 == False:
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[5], cv2.TM_CCOEFF_NORMED)
					result_hyaku = cv2.matchTemplate(
						hyaku_img, hyaku_imgs[3], cv2.TM_CCOEFF_NORMED)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
					if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
						combo = 400
						playable = False
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo400 = True
				if combo500 == False:
					result_zyuu = cv2.matchTemplate(
						zyuu_img, zyuu_imgs[6], cv2.TM_CCOEFF_NORMED)
					result_hyaku = cv2.matchTemplate(
						hyaku_img, hyaku_imgs[4], cv2.TM_CCOEFF_NORMED)
					min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
					l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
					if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True:
						combo = 500
						playable = False
						comboend = True
						if detected == True and name != "_":
							se_thread = threading.Thread(target=mod_music_play(combo, name))
							se_thread.start()
						else:
							m_s_thread = threading.Thread(
								target=thread_music_play(combo, muse, aqours))
							m_s_thread.start()
						combo500 = True
				if k & 0xFF == ord('b') or comboend == True:
					#booleans initializer
					analyzable = False
					combo = 0
					combo10 = False
					combo50 = False
					combo100 = False
					combo200 = False
					combo300 = False
					combo400 = False
					combo500 = False
					comboend = False
					resetcounter += 1
					if character_detectable == True:
						character_detectable = False
						name = "_"
					if detected == True:
						character_detectable = False
						name = "_"
						detected = False
					if name != "_":
						name = "_"
					if resetcounter >= 5:
						muse = True
						aqours = False
						resetcounter = 0
					dt = datetime.now()
					dt = dt + timedelta(minutes=1) + timedelta(seconds=32)
					time = dt.strftime("%M:%S")
					clip_temp = difficulty + " " + time
					pyperclip.copy(clip_temp)
				if k & 0xFF == ord('v'):
					muse = True
					aqours = False
			if k & 0xFF == ord('x'):
				if difficulty == "EX":
					difficulty = "TEC"
				elif difficulty == "TEC":
					difficulty = "EX"
			if k & 0xFF == ord('b'):
				if first_time:
					dt = datetime.now()
					dt = dt + timedelta(seconds=45)
					first_time = False
				else:
					dt = datetime.now()
					dt = dt + timedelta(minutes=1) + timedelta(seconds=10)
				time = dt.strftime("%M:%S")
				clip_temp = difficulty + " " + time
				pyperclip.copy(clip_temp)
			if k & 0xFF == ord('q') or k & 0xFF == 27:
				if picture_Takeble == 1 and device_detecting == 0:
					hyaku_img = frame[top:bottom, left:hyaku_right]
					zyuu_img = frame[top:bottom, hyaku_right:zyuu_right]
					ichi_img = frame[top:bottom, zyuu_right:ichi_right]
					face_img = frame[facetop:facebottom, faceleft:faceright]
					hyaku_img = preprocessing(hyaku_img, "h_digit", True)
					zyuu_img = preprocessing(zyuu_img, "t_digit", True)
					face_img = preprocessing(face_img, "face", True)
					ichi_img = preprocessing(ichi_img, "u_digit", True)
					cv2.imwrite("SIF_window.png", frame)
				break
		else:
			break

	cap.release()
	cv2.destroyAllWindows()
	pyperclip.copy(clip)

argvs = sys.argv
argc = len(argvs)
if argc < 15 :
	print("Error!! need more args")
else :
	main_process(int(argvs[1]), int(argvs[2]), int(argvs[3]), int(argvs[4]), int(argvs[5]), int(argvs[6]), int(argvs[7]), int(argvs[8]), int(argvs[9]), int(argvs[10]), int(argvs[11]), int(argvs[12]), int(argvs[13]), int(argvs[14]))
"""
device_index,
Combo_img_x,
Combo_img_y,
Combo_digits_width_numud,
Combo_digits_height_numud,
face_x_numud,
face_y_numud,
face_width_numud,
face_height_numud,
device_detecting,
picture_Takeble,
cutting_showable,
div,
chara_choosable
"""
