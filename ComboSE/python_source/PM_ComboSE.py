import numpy as np
import cv2
import threading
import pygame
import random
import os
import concurrent.futures
import pyperclip
from datetime import datetime, timedelta
import csv
#import os.path
#import sys

def preprocessing(img, name, ts=144) :
	#cv2.imwrite("raw_"+str(name)+"img.png", img)
	img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
	img = cv2.GaussianBlur(img, (3, 3), 0)
	img = cv2.resize(img, (28, 28))
	res, img = cv2.threshold(img, ts, 255, cv2.THRESH_BINARY)
	img = 255 - img
	img = cv2.bitwise_not(img)
	
	#img = img.astype(np.float32)
	#cv2.imwrite(name, img)
	#img = np.array(img).reshape(1, 784)
	return img

def thread_music_play(combo, muse=True, aqours=False) :
	file_selector = ""
	if muse == True and aqours == True :
		all_character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico", "chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"]
		file_selector = random.choice(all_character_name_list)
	elif muse == True :
		character_name_list = ["honoka", "eli", "kotori", "umi", "rin", "maki", "nozomi", "hanayo", "nico"]
		file_selector = random.choice(character_name_list)
	elif aqours == True :
		aq_character_name_list = ["chika", "riko", "kanan", "dia", "you", "yoshiko", "hanamaru", "mari", "ruby"]
		file_selector = random.choice(aq_character_name_list)
	pygame.mixer.init()
	if combo > 300 :
		combo = None; combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+file_selector+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)

def beforeprocessing() :
	ichi_imgs = []; zyuu_imgs = []; hyaku_imgs = []
	character_face_imgs = [] #onpu_imgs = []
	#paths = ["ichi", "zyuu", "hyaku", "faces", "onpu"]
	paths = ["ichi", "zyuu", "hyaku", "faces"]
	for i in range(4) :
		img_lists = os.listdir(paths[i])
		#print(img_lists)
		"""
		['ichi_0.png', 'ichi_10.png', 'ichi_100.png', 'ichi_200.png', 'ichi_300.png', 'ichi_400.png', 'ichi_401.png', 'ichi_50.png', 'ichi_500.png']
		['zyuu_0.png', 'zyuu_10.png', 'zyuu_100.png', 'zyuu_200.png', 'zyuu_300.png', 'zyuu_400.png', 'zyuu_50.png', 'zyuu_500.png', 'zyuu_9.png']
		['hyaku_1.png', 'hyaku_2.png', 'hyaku_3.png', 'hyaku_4.png', 'hyaku_400.png', 'hyaku_5.png', 'hyaku_500.png']
		['chika.png', 'hanayo.png', 'kotori.png', 'maru.png', 'riko.png', 'riko_1.png','riko_2.png', 'rin.png', 'umi_1.png', 'umi_2.png', 'yoshiko.png', 'you.png']
		"""
		lists_length = len(img_lists)
		#print(img_lists)
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
			#elif i == 4 :
				#onpu_imgs.append(imgSrc)

	#return ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs, onpu_imgs
	return ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs

def mod_music_play(combo, name) :
	if name == "_" :
		print("Error!!:character name is illiegal value.")
		print("name is "+ str(name))
		thread_music_play(combo)
		return
	if name != "honoka" and name != "eli" and name != "kotori" and name != "umi" and name != "rin" and name != "maki" and name != "nozomi" and name != "hanayo" and name != "nico" and name != "chika" and name != "riko" and name != "kanan" and name != "dia" and name != "you" and name != "yoshiko" and name != "hanamaru" and name != "mari" and name != "ruby" and name != "_" :
		print("Error!!:Unknown character name Error!!")
		print("name is "+str(name))
		thread_music_play(combo)
		return
	pygame.mixer.init()
	if combo > 300 :
		combo = None; combo = "eternal"
	pygame.mixer.music.load("comboSEs\\"+name+"\\"+str(combo)+".mp3")
	pygame.mixer.music.play(1)

def result_analyze(frame, bpm_data,debug=False) :
	combo = 0; score = 0; music_name = "_"
	perfect = 0; great = 0; good = 0
	bad = 0; miss = 0; score_width = 40
	score_left = 729; score_top = 771; score_bottom = 833
	hkscore_img = frame[score_top:score_bottom, score_left:score_width+score_left]
	tkscore_img = frame[score_top:score_bottom, score_left+score_width:2*score_width+score_left]
	ukscore_img = frame[score_top:score_bottom, score_left+2*score_width:score_left+3*score_width]
	hscore_img = frame[score_top:score_bottom, score_left+3*score_width:score_left+4*score_width]
	tscore_img = frame[score_top:score_bottom, score_left+4*score_width:score_left+5*score_width]
	uscore_img = frame[score_top:score_bottom, score_left+5*score_width:score_left+6*score_width]
	hkscore_img = preprocessing(hkscore_img, "_", 192)
	tkscore_img = preprocessing(tkscore_img, "_", 192)
	ukscore_img = preprocessing(ukscore_img, "_", 192)
	hscore_img = preprocessing(hscore_img, "_", 192)
	tscore_img = preprocessing(tscore_img, "_", 192)
	uscore_img = preprocessing(uscore_img, "_", 192)
	if debug :
		cv2.imwrite("100k.png", hkscore_img)
		cv2.imwrite("10k.png", tkscore_img)
		cv2.imwrite("1k.png", ukscore_img)
		cv2.imwrite("hscore.png", hscore_img)
		cv2.imwrite("tscore.png", tscore_img)
		cv2.imwrite("uscore.png", uscore_img)
	cmbtp = 454; cmble = 1449; cmbwi = 38; cmbbt = 506
	kcombo = frame[cmbtp:cmbbt, cmble:cmble+cmbwi]
	hcombo = frame[cmbtp:cmbbt, cmble+cmbwi:cmble+2*cmbwi]
	tcombo = frame[cmbtp:cmbbt, cmble+2*cmbwi:cmble+3*cmbwi]
	ucombo = frame[cmbtp:cmbbt, cmble+3*cmbwi:cmble+4*cmbwi]
	kcombo = preprocessing(kcombo,"_", 192)
	hcombo = preprocessing(hcombo, "_", 192)
	tcombo = preprocessing(tcombo, "_", 192)
	ucombo = preprocessing(ucombo, "_", 192)
	if debug :
		cv2.imwrite("kcombo.png", kcombo)
		cv2.imwrite("hcombo.png", hcombo)
		cv2.imwrite("tcombo.png", tcombo)
		cv2.imwrite("ucombo.png", ucombo)
	pertp = 555; perle = 1449; perwi = 38; perbt = 607
	kper = frame[pertp:perbt, perle:perle+perwi]
	hper = frame[pertp:perbt, perle+perwi:perle+2*perwi]
	tper = frame[pertp:perbt, perle+2*perwi:perle+3*perwi]
	uper = frame[pertp:perbt, perle+3*perwi:perle+4*perwi]
	kper = preprocessing(kper, "_", 192)
	hper = preprocessing(hper, "_", 192)
	tper = preprocessing(tper, "_", 192)
	uper = preprocessing(uper, "_", 192)
	if debug :
		cv2.imwrite("kper.png", kper)
		cv2.imwrite("hper.png", hper)
		cv2.imwrite("tper.png", tper)
		cv2.imwrite("uper.png", uper)
	gretp = 626; grebt = 678
	kgre = frame[gretp:grebt, perle:perle+perwi]
	hgre = frame[gretp:grebt, perle+perwi:perle+2*perwi]
	tgre = frame[gretp:grebt, perle+2*perwi:perle+3*perwi]
	ugre = frame[gretp:grebt, perle+3*perwi:perle+4*perwi]
	kgre = preprocessing(kgre, "_", 192)
	hgre = preprocessing(hgre, "_", 192)
	tgre = preprocessing(tgre, "_", 192)
	ugre = preprocessing(ugre, "_", 192)
	if debug :
		cv2.imwrite("kgre.png",kgre)
		cv2.imwrite("hgre.png",hgre)
		cv2.imwrite("tgre.png",tgre)
		cv2.imwrite("ugre.png",ugre)
	gdtp = 698; gdbt = 750
	kgd = frame[gdtp:gdbt, perle:perle+perwi]
	hgd = frame[gdtp:gdbt, perle+perwi:perle+2*perwi]
	tgd = frame[gdtp:gdbt, perle+2*perwi:perle+3*perwi]
	ugd = frame[gdtp:gdbt, perle+3*perwi:perle+4*perwi]
	kgd = preprocessing(kgd, "_", 192)
	hgd = preprocessing(hgd, "_", 192)
	tgd = preprocessing(tgd, "_", 192)
	ugd = preprocessing(ugd, "_", 192)
	if debug :
		cv2.imwrite("kgd.png", kgd)
		cv2.imwrite("hgd.png", hgd)
		cv2.imwrite("tgd.png", tgd)
		cv2.imwrite("ugd.png", ugd)
	bdtp = 772; bdbt = 824
	tbd = frame[bdtp:bdbt, perle+2*perwi:perle+3*perwi]
	ubd = frame[bdtp:bdbt, perle+3*perwi:perle+4*perwi]
	tbd = preprocessing(tbd, "_", 192)
	ubd = preprocessing(ubd, "_", 192)
	mstp = 844; msbt = 896
	tms = frame[mstp:msbt, perle+2*perwi:perle+3*perwi]
	ums = frame[mstp:msbt, perle+3*perwi:perle+4*perwi]
	tms = preprocessing(tms, "_", 192)
	ums = preprocessing(ums, "_", 192)
	if debug :
		cv2.imwrite("tbd.png", tbd)
		cv2.imwrite("ubd.png", ubd)
		cv2.imwrite("tms.png", tms)
		cv2.imwrite("ums.png", ums)
	
	music_thumbnail = frame[173:366, 1282:1507]
	music_thumbnail = preprocessing(music_thumbnail, "_")
	if debug :
		cv2.imwrite("music_thumbnail.png", music_thumbnail)
	#1282 173

	path_lists = ["hcombo_nums", "tcombo_nums", "ucombo_nums"]
	sep_combo = 0
	for p in path_lists :
		lists = os.listdir(p)
		for f in lists :
			if sep_combo == 0 :
				temp = cv2.imread(p+"\\"+f, 0)
				res_h = cv2.matchTemplate(hcombo, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_h)
				if max_val >= 0.8 :
					f, _ = os.path.splitext(f)
					combo += int(f)*100
			elif sep_combo == 1 :
				temp = cv2.imread(p+"\\"+f, 0)
				res_t = cv2.matchTemplate(tcombo, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
				if max_val >= 0.9 :
					f, _ = os.path.splitext(f)
					combo += int(f)*10
			elif sep_combo == 2 :
				temp = cv2.imread(p+"\\"+f, 0)
				res_u = cv2.matchTemplate(ucombo, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
				if max_val >= 0.9 :
					f, _  = os.path.splitext(f)
					combo += int(f)
		sep_combo += 1
	sep_per = 0
	per_lists = ["hper_nums", "tper_nums", "uper_nums"]
	for p in per_lists :
		lists = os.listdir(p)
		for f in lists :
			temp = cv2.imread(p+"\\"+f, 0)
			if sep_per == 0 :
				res_h = cv2.matchTemplate(hper, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_h)
				if max_val >= 0.8 :
					f, _ = os.path.splitext(f)
					perfect += int(f)*100
			elif sep_per == 1 :
				res_t = cv2.matchTemplate(tper, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
				if max_val >= 0.9 :
					f, _ = os.path.splitext(f)
					perfect += int(f)*10
			elif sep_per == 2 :
				res_u = cv2.matchTemplate(uper, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
				if max_val >= 0.9 :
					f, _ = os.path.splitext(f)
					perfect += int(f)
		sep_per += 1
	sep_gre = 0
	gre_lists = ["tgre_nums", "ugre_nums"]
	for p in gre_lists :
		lists = os.listdir(p)
		for f in lists :
			temp = cv2.imread(p+"\\"+f, 0)
			if sep_gre == 0 :
				res_t = cv2.matchTemplate(tgre, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
				if max_val >= 0.8 :
					f, _ = os.path.splitext(f)
					great += int(f)*10
			elif sep_gre == 1 :
				res_u = cv2.matchTemplate(ugre, temp, cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
				if max_val >= 0.95 :
					f, _ = os.path.splitext(f)
					great += int(f)
		sep_gre += 1
	lists = os.listdir("ugd_nums")
	for f in lists :
		temp = cv2.imread("ugd_nums\\"+f, 0)
		res_u = cv2.matchTemplate(ugd, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
		if max_val >= 0.8 :
			f, _ = os.path.splitext(f)
			good += int(f)
	lists = os.listdir("ubd_nums")
	for f in lists :
		temp = cv2.imread("ubd_nums\\"+f, 0)
		res_u = cv2.matchTemplate(ubd, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
		if max_val >= 0.8 :
			f, _ = os.path.splitext(f)
			bad += int(f)
	lists = os.listdir("ums_nums")
	for f in lists :
		temp = cv2.imread("ums_nums\\"+f, 0)
		res_u = cv2.matchTemplate(ums, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
		if max_val >= 0.8 :
			f, _ = os.path.splitext(f)
			miss += int(f)
	lists = os.listdir("music_thumbnails")
	for f in lists :
		temp = cv2.imread("music_thumbnails\\"+f, 0)
		res_img = cv2.matchTemplate(music_thumbnail, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_img)
		if max_val >= 0.8 :
			f, _ = os.path.splitext(f)
			if f == "kit_seishunga_kikoeru" :
				music_name = "きっと青春が聞こえる"
			if f == "RAN_koino_signal_Rinrinrin" :
				music_name = "[乱]恋のシグナルRin rin rin!"
			if f == "RAN_binetukara_mystery" :
				music_name = "[乱]微熱からMystery"
			if f == "RAN_musicstart":
				music_name = "[乱]Music S.T.A.R.T!!"
			if f == "private_wars":
				music_name = "Private Wars"
			if f == "mermaid_festa_vol1":
				music_name = "Mermaid festa vol.1"
			if f == "bokuraha_imanonakade" :
				music_name = "僕らは今のなかで"
			if f == "kirakirasensation" :
				music_name = "KiRa-KiRa Sensation!"
			if f == "RAN_kodokuna_heaven" :
				music_name = "[乱]孤独なHeaven"
			if f == "no_brand_girls" :
				music_name = "No brand girls"
			if f == "colorful_voice" :
				music_name = "COLORFUL VOICE"
			if f == "kirakirasensation" and perfect + great + good + bad + miss == 555:
				music_name = "Happy maker!"
			if f == "zuruiyo_magnetic_today" :
				music_name = "ずるいよ Magnetic today"
			if f == "eien_frends" :
				music_name = "永遠フレンズ"
			if f == "saiteide_saikouno_paradiso" :
				music_name = "最低で最高のParadiso"
			if f == "dreamin_gogo" :
				music_name = "Dreamin' GO! GO!!"
			if f == "start_dash" :
				music_name = "START:DASH!!"
			if f == "mermaid_festa_vol2" :
				music_name = "Mermaid festa vol.2"
			if f == "blueberry_train" :
				music_name = "ぶる～べりぃ とれいん"
			if f == "lovelessworld" :
				music_name = "LOVELESS WORLD"
			if f == "hutari_happiness" :
				music_name = "ふたりハピネス"
			if f == "future_style" :
				music_name = "Future style"
			if f == "soldier_game" :
				music_name = "soldier game"
			if f == "shangri-la_shower" :
				music_name = "Shangri-La Shower"
			if f == "korekarano_someday" :
				music_name = "これからのSomeday"
			if f == "hello_hosiwokazoete" :
				music_name = "Hello_星を数えて"
			if f == "blueberry_train" and perfect + great + good + bad + miss == 579 :
				music_name = "告白日和、です！"
			if f == "kit_seishunga_kikoeru" and perfect + great + good + bad + miss == 421:
				music_name = "輝夜の城で踊りたい"
			if f == "genki_zenkai_day_day_day" :
				music_name = "元気全開DAY!DAY!DAY!"
			if f == "aozora_jumping_heart" :
				music_name = "青空Jumping Heart"
			if f == "mirainobokurahasitteruyo" :
				music_name = "未来の僕らは知ってるよ"
			if f == "water_blue_new_world" :
				music_name = "WATER BLUE NEW WORLD"
			if f == "thrilling_one_way" :
				music_name = "スリリング・ワンウェイ"
			if f == "todokanai_hoshidatoshitemo" :
				music_name = "届かない星だとしても"
			if f == "todokanai_hoshidatoshitemo" and perfect + great + good + bad + miss == 544:
				music_name = "恋になりたいAQUARIUM"
			if f == "kimetayo_hand_in_hand" :
				music_name = "決めたよHand in Hand"
			if f == "kimetayo_hand_in_hand" and perfect + great + good + bad + miss == 447 :
				music_name = "ダイスキだったらダイジョウブ！"
			if f == "mijuku_dreamer" :
				music_name = "未熟DREAMER"
			if f == "gsenzyono_cinderella" :
				music_name = "G線上のシンデレラ"
			if f == "happy_party_train" :
				music_name = "HAPPY PARTY TRAIN"
			if f == "kowareyasuki" :
				music_name = "コワレヤスキ"
			if f == "sky_journey" :
				music_name = "SKY JOURNEY"
			if f == "mymai_tonight" :
				music_name = "MY舞☆TONIGHT"
			if f == "awaken_the_power" :
				music_name = "Awaken the power"
			if f == "water_blue_new_world" and perfect + great + good + bad + miss == 514 :
				music_name = "WONDERFUL STORIES"
			if f == "self_control" :
				music_name = "SELF CONTROL!!"
			if f == "step_zero_to_one" :
				music_name = "Step! ZERO to ONE"
			if f == "new_winding_road" :
				music_name = "New winding road"
			if f == "kisekihikaru" :
				music_name = "キセキヒカル"
			if f == "beginner's_sailing" :
				music_name = "Beginner's Sailing"
			if f == "hop_step_why" :
				music_name = "ホップ・ステップ・ワーイ！"
	dt = datetime.now()
	time = dt.strftime("%Y-%m-%d %H:%M:%S")

	file = open('music_result.csv', 'a')
	writer = csv.writer(file, lineterminator='\n')
	writelist = []
	writelist.append(music_name)
	if not bpm_data is None :
		if music_name in "[乱]" :
			music_name.replace('[乱]', '')
		bpm = set_title_to_bpm(music_name, bpm_data)
		if not bpm == "" :
			writelist.append(str(bpm))
	writelist.append(str(combo))
	writelist.append(str(perfect))
	writelist.append(str(great))
	writelist.append(str(good))
	writelist.append(str(bad))
	writelist.append(str(miss))
	writelist.append(time)

	writer.writerow(writelist)
	file.close()

class music :
	def __init__(self, title, bpm) :
		self.title = title
		self.bpm = bpm

def csv_to_list() :
	csv_file = open('bpm.csv', 'r')
	with csv_file :
		src_list = csv.reader(csv_file, delimiter=",", doublequote=True, lineterminator="\n", quotechar='"', skipinitialspace=True)
		music_list = []
		if src_list is None :
			return None
		for src in src_list :
			music_list.append(music(src[1], src[2]))
	return music_list

def set_title_to_bpm(music_name, bpm_data) :
	if bpm_data is None :
		return ""
	for mdata in bpm_data :
		if mdata.title == music_name :
			return str(mdata.bpm)
	return ""

def score_matchpt_detect(frame, debug, name="_") :
	src_copy = frame
	#press "e" key push to run this method.
	#224 304 {left, top}
	#289 326 {right, bot}

	frame_height, frame_width = frame.shape[:2]
	
	#template image load
	temp = cv2.imread("user_pane\\img_bin.png", 0)
	
	# get frame color settings
	frame = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

	# userpink mask settings
	upper_userpink = np.array([230, 200, 255])
	lower_userpink = np.array([120, 80, 190])
	
	#get masked img
	img_mask = cv2.inRange(frame, lower_userpink, upper_userpink)
	
	#cvt black and pink
	img_bin = cv2.bitwise_and(frame, frame, mask=img_mask)
	
	#cvt pink to white
	img_bin = cv2.cvtColor(img_bin, cv2.COLOR_HSV2BGR)
	img_bin = cv2.cvtColor(img_bin, cv2.COLOR_BGR2GRAY)
	res, img_bin = cv2.threshold(img_bin, 5, 255, cv2.THRESH_BINARY)
	if debug :
		cv2.imwrite("frame_bin.png", img_bin)

	#detect location bin detection.
	res_pane = cv2.matchTemplate(img_bin, temp, cv2.TM_CCOEFF_NORMED)
	min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_pane)
	
	if debug :
		print(f"min_loc : {min_loc}, max_loc : {max_loc}")
		print(f"min_val : {min_val}, max_val : {max_val}")
	if max_val > 0.52 :
		upane_left, upane_top = max_loc
	else :
		print(f"can't detect. max_val : {max_val}, max_loc : {max_loc}")
		return 0
	if debug :
		print(f"upane_left : {upane_left}, upane_top : {upane_top}")
	upane_height = 638; upane_width = 338
	if frame_width < upane_left + upane_width or frame_height < upane_top + upane_height :
		print("size error!")
		print(f"frame_height : {frame_height}, frame_width : {frame_width}")
		print(f"detectloc_left : {upane_left}, right_maxloc : {upane_left+upane_width}")
		print(f"detectloc_top : {upane_top}, bottom_maxloc : {upane_top+upane_height}")
		return 0
	if debug :
		raw_user_pane = src_copy[upane_top:upane_top+upane_height, upane_left:upane_left+upane_width]
		cv2.imwrite("raw_user_pane.png", raw_user_pane)
	ranking = ""
	if 1346 == upane_left :
		ranking = "4th"
	elif 982 == upane_left :
		ranking = "3rd"
	elif 617 == upane_left :
		ranking = "2nd"
	
	user_pane = img_bin[upane_top:upane_top+upane_height, upane_left:upane_left+upane_width]
	res, user_pane_img = cv2.threshold(user_pane, 200, 255, cv2.THRESH_BINARY)
	if debug :
		cv2.imwrite("user_pane_bin.png", user_pane_img)
	#324 -> 304 348 -> 326
	tkmatchpt_img = user_pane_img[304:326, 224:235]
	kmatchpt_img = user_pane_img[304:326, 237:250]
	hmatchpt_img = user_pane_img[304:326, 250:263]
	tmatchpt_img = user_pane_img[304:326, 263:276]
	umatchpt_img = user_pane_img[304:326, 276:289]
	if debug :
		cv2.imwrite("tkmatchpt_img.png", tkmatchpt_img)
		cv2.imwrite("kmatchpt_img.png", kmatchpt_img)
		cv2.imwrite("hmatchpt_img.png", hmatchpt_img)
		cv2.imwrite("tmatchpt_img.png", tmatchpt_img)
		cv2.imwrite("umatchpt_img.png", umatchpt_img)
	matchpt = 0
	div = 0.85
	
	lists = os.listdir("tkmatchpt")
	for f in lists :
		temp = cv2.imread("tkmatchpt\\"+f, 0)
		res_tk = cv2.matchTemplate(tkmatchpt_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_tk)
		if max_val >= div :
			f, _ = os.path.splitext(f)
			f = parseInt(f)
			matchpt += int(f)*10000
	lists = os.listdir("kmatchpt")
	for f in lists :
		temp = cv2.imread("kmatchpt\\"+f, 0)
		res_k = cv2.matchTemplate(kmatchpt_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_k)
		if max_val >= div :
			f, _ = os.path.splitext(f)
			f = parseInt(f)
			matchpt += int(f)*1000
	lists = os.listdir("hmatchpt")
	for f in lists :
		temp = cv2.imread("hmatchpt\\"+f, 0)
		res_h = cv2.matchTemplate(hmatchpt_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_h)
		if max_val >= div :
			f, _ = os.path.splitext(f)
			f = parseInt(f)
			matchpt += int(f)*100
	lists = os.listdir("tmatchpt")
	for f in lists :
		temp = cv2.imread("tmatchpt\\"+f, 0)
		res_t = cv2.matchTemplate(tmatchpt_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= div :
			f, _ = os.path.splitext(f)
			f = parseInt(f)
			matchpt += int(f)*10
	lists = os.listdir("umatchpt")
	for f in lists :
		temp = cv2.imread("umatchpt\\"+f, 0)
		res_u = cv2.matchTemplate(umatchpt_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_u)
		if max_val >= div :
			f, _ = os.path.splitext(f)
			f = parseInt(f)
			matchpt += int(f)

	if name != "_" and ranking != "" :
		if debug :
			print(f"character name : {name}")
	
	if debug :
		print(f"match pt : {matchpt}.")
		print(f"ranking : {ranking}.")
	return matchpt

def parseInt(f) :
	if str(1) in f :
		return "1"
	elif str(2) in f :
		return "2"
	elif str(3) in f :
		return "3"
	elif str(4) in f :
		return "4"
	elif str(5) in f :
		return "5"
	elif str(6) in f :
		return "6"
	elif str(7) in f :
		return "7"
	elif str(8) in f :
		return "8"
	elif str(9) in f :
		return "9"
	elif str(0) in f :
		return "0"
	else :
		print("error!!")
		return "0"

"""
def combo_cutin(combo, frame, name="_"):
	#this method is showing combo-cut-in-character.
	# https://qiita.com/qinojp/items/d2d9a68a962b34b62888
	img_lists = []
"""

def set_time_table(first_time, dt) :
	abs_dt = datetime.now()
	if dt is None or first_time :
		dt = abs_dt + timedelta(seconds=45)
		return dt
	else :
		delaydt = dt - timedelta(seconds=10)
		nextdt = dt + timedelta(minutes=3) + timedelta(seconds=20)
		if abs_dt < delaydt :
			return dt
		elif abs_dt > nextdt :
			dt = abs_dt + timedelta(seconds=45)
			return dt
		else :
			dt = dt + timedelta(minutes=3) + timedelta(seconds=30)
			return dt
	return dt

def get_room_number(frame, debug) :
	#864 199 {left, top}
	#1010 234 {right, bot}
	
	#number cutting
	number_top = 199; number_bot = 234; number_left = 864; number_right = 1012
	number_img = frame[number_top:number_bot, number_left:number_right]

	#number cutting
	number_height = 35
	hk_number_img = number_img[0:number_height, 0:23]
	tk_number_img = number_img[0:number_height, 23:48]
	k_number_img = number_img[0:number_height, 48:74]
	h_number_img = number_img[0:number_height, 73:99]
	t_number_img = number_img[0:number_height, 99:124]
	u_number_img = number_img[0:number_height, 124:148]

	#cvtFullcolor2Bin
	hk_number_img = preprocessing(hk_number_img, "_")
	tk_number_img = preprocessing(tk_number_img, "_")
	k_number_img = preprocessing(k_number_img, "_")
	h_number_img = preprocessing(h_number_img, "_")
	t_number_img = preprocessing(t_number_img, "_")
	u_number_img = preprocessing(u_number_img, "_")

	#img.parseInt
	room_number = 0
	filename_lists = os.listdir("room_number_hk")
	for filename in filename_lists:
		temp = cv2.imread("room_number_hk\\"+filename, 0)
		res_t = cv2.matchTemplate(hk_number_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= 0.8:
			filename, _ = os.path.splitext(filename)
			room_number += int(filename)*100000
	filename_lists = os.listdir("room_number_tk")
	for filename in filename_lists:
		temp = cv2.imread("room_number_tk\\"+filename, 0)
		res_t = cv2.matchTemplate(tk_number_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= 0.8:
			filename, _ = os.path.splitext(filename)
			room_number += int(filename)*10000
	filename_lists = os.listdir("room_number_k")
	for filename in filename_lists:
		temp = cv2.imread("room_number_k\\"+filename, 0)
		res_t = cv2.matchTemplate(k_number_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= 0.8:
			filename, _ = os.path.splitext(filename)
			room_number += int(filename)*1000
	filename_lists = os.listdir("room_number_h")
	for filename in filename_lists:
		temp = cv2.imread("room_number_h\\"+filename, 0)
		res_t = cv2.matchTemplate(h_number_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= 0.8:
			filename, _ = os.path.splitext(filename)
			room_number += int(filename)*100
	filename_lists = os.listdir("room_number_t")
	for filename in filename_lists:
		temp = cv2.imread("room_number_t\\"+filename, 0)
		res_t = cv2.matchTemplate(t_number_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= 0.8:
			filename, _ = os.path.splitext(filename)
			room_number += int(filename)*10
	filename_lists = os.listdir("room_number_u")
	for filename in filename_lists:
		temp = cv2.imread("room_number_u\\"+filename, 0)
		res_t = cv2.matchTemplate(u_number_img, temp, cv2.TM_CCOEFF_NORMED)
		min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_t)
		if max_val >= 0.8:
			filename, _ = os.path.splitext(filename)
			room_number += int(filename)

	if debug :
		cv2.imwrite("hk_number_img.png", hk_number_img)
		cv2.imwrite("tk_number_img.png", tk_number_img)
		cv2.imwrite("k_number_img.png", k_number_img)
		cv2.imwrite("h_number_img.png", h_number_img)
		cv2.imwrite("t_number_img.png", t_number_img)
		cv2.imwrite("u_number_img.png", u_number_img)
		cv2.imwrite("number.png", number_img)
		print(f"room_number = {room_number}")

	#number int2str
	room_number_formated = '{0:06d}'.format(room_number)
	return room_number_formated

#======================================================================================================================================================================================================================================================================
#main_process key: "a" -> start analyze "b" -> end analyze "s" -> aqours mode.(and rating_mode off) "d" -> all_star mode. "z" -> detect mode. "q" -> quit process. "u" -> umi mode. "r" -> riko mode. "x" -> difficulty changeing.(EX->TEC TEC->EX and rating_mode on)
#======================================================================================================================================================================================================================================================================
debug = False
dt = None

clip = ""
if pyperclip.is_available() :
	clip = pyperclip.paste()

"""
available_URL = False
argvs = sys.argv
if len(argvs) == 2 :
	available_URL = True;url = argvs[1]
"""

code_checkable = False #if code_checkable = true do line 129 to comment in.

bpm_data = csv_to_list()

#ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs, onpu_imgs = beforeprocessing()
ichi_imgs, zyuu_imgs, hyaku_imgs, character_face_imgs = beforeprocessing()

if not code_checkable :
	cap = cv2.VideoCapture(1)

analyzable = False; combo = 0; combo10 = False
combo50 = False; combo100 = False; combo200 = False
combo300 = False; combo400 = False; combo500 = False
comboend = False; muse = True; aqours = False
playmusictimer = 0; playable = True; difficulty = "EX"
rating_mode = False

character_detectable = False
detectabletimer = 0; detected = False; name = "_"

left = 730; right = 960; top = 415; bottom = 501; dif = 76
hyaku_right = left+dif+1; zyuu_right = hyaku_right+dif-1; ichi_right = zyuu_right+dif+1
faceleft = 855; faceright = 1067; facetop = 820; facebottom = 1025
"""
onputop = 190; onpubot = 409; onpulef = 823; onpurig = 1109
"""

first_time = True

#"""
while cap.isOpened() :
	ret, frame = cap.read()
	if ret == True :
		if playable == False :
			playmusictimer += 1
			if playmusictimer >= 60 :
				playable = True; playmusictimer = 0
		"""
		cframe = frame
		
		if analyzable == True and debug :
			cv2.rectangle(cframe, (hyaku_right,bottom), (left, top), (0, 0, 255), 3)
			cv2.rectangle(cframe, (zyuu_right, bottom), (hyaku_right, top), (0, 0, 255), 3)
			cv2.rectangle(cframe, (ichi_right, bottom), (zyuu_right, top), (0, 0, 255), 3)
		"""
		
		cv2.imshow('LoveLive! School idol Festival', frame)
		k = cv2.waitKey(1)
		if k & 0xFF == ord('a') :
			analyzable = True
		if k & 0xFF == ord('s') :
			muse = False; aqours = True
			if not muse or (muse and aqours) :
				muse = True; aqours = False
			if rating_mode :
				rating_mode = False
		if k & 0xFF == ord('d') :
			muse = True; aqours = True
		if k & 0xFF == ord('z') :
			character_detectable = True
		if k & 0xFF == ord('u') :
			name = "umi"; detected = True
		if k & 0xFF == ord('r') :
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

			#onpu_img = frame[onputop:onpubot, onpulef:onpurig]
			#onpu_img = preprocessing(onpu_img, "_")
		"""
		fps_counter += 1
		if fps_counter % 20 == 0 :
			onpu_img = frame[onputop:onpubot, onpulef:onpurig]
			onpu_img = preprocessing(onpu_img, "_")
			res_onpu = cv2.matchTemplate(onpu_img, onpu_imgs[0], cv2.TM_CCOEFF_NORMED)
			l_v_o, h_v_o, l_l_o, h_l_o = cv2.minMaxLoc(res_onpu)
			if h_v_o >= 0.8 :
				analyzable = True; character_detectable = True
			if h_v_o <= 0.3 :
				analyzable = False; combo = 0; combo10 = False
				combo50 = False; combo100 = False; combo200 = False
				combo300 = False; combo400 = False; combo500 = False
				fps_counter = 0
		"""

		if k & 0xFF == ord('x'):
			if not rating_mode:
				rating_mode = True
			if difficulty == "EX":
				difficulty = "TEC"
			elif difficulty == "TEC":
				difficulty = "EX"
		if k & 0xFF == ord('b'):
			if first_time:
				dt = set_time_table(first_time, dt)
				first_time = False
			else:
				dt = set_time_table(first_time, dt)
			time = dt.strftime("%M:%S")
			clip_temp = difficulty + " " + time
			pyperclip.copy(clip_temp)
		"""
		result_analyzer needs improvement.
		"""
		if k & 0xFF == ord('d'):
			result_analyze(frame, bpm_data, debug)
			#result saver is not available. 2018/07/04.
			#the reason is why this method is not complete.
			#this method is unsafe.
		if k & 0xFF == ord('e') and rating_mode:
			match_pt = score_matchpt_detect(frame, debug)
			dt = set_time_table(first_time, dt)
			time = dt.strftime("%M:%S")
			clip_temp = "スコマpt:" + str(match_pt) + " " + difficulty + " " + time
			pyperclip.copy(clip_temp)

			#score match pt detector method is not complete.
			#this method is unsafe.
			#マッチｐｔディテクターは認識終了時にbボタンを押して処理開始.
			#処理が終わったら、通常の時間指定の文章と連結して、pyperclipにコピーすることを想定中.
		if k & 0xFF == ord('w') and rating_mode:
			match_pt = score_matchpt_detect(frame, True)
			dt = set_time_table(first_time, dt)
			time = dt.strftime("%M:%S")
			clip_temp = difficulty + " " + time
			pyperclip.copy(clip_temp)
			#matchptdetecter debug mode.
		#if k & 0xFF == ord('f') :
			#room_number = get_room_number(frame, debug)
			#pyperclip.copy(room_number)

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
					name = "chika"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[1], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "hanayo"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[2], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "hanayo"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[3], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "kotori"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[4], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "hanamaru"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[5], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "riko"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[6], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "riko"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[7], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "riko"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[8], cv2.TM_CCOEFF_NORMED)
				min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res_faces)
				if max_val >= 0.8 :
					name = "riko"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[9], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "rin"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[10], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "umi"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[11], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "umi"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[12], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "umi"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[13], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "yoshiko"; detected = True; character_detectable = False
				res_faces = cv2.matchTemplate(face_img, character_face_imgs[14], cv2.TM_CCOEFF_NORMED)
				l_v_f, h_v_f, l_l_f, h_l_f = cv2.minMaxLoc(res_faces)
				if h_v_f >= 0.8 :
					name = "you"; detected = True; character_detectable = False
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
				if max_val_i >= 0.7 and max_val_z >= 0.7 and playable == True :
					combo = 10; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo10 = True
			if combo50 == False :
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[7], cv2.TM_CCOEFF_NORMED)
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True :
					combo = 50; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo50 = True
				result_ichi = cv2.matchTemplate(ichi_img, ichi_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_i, max_val_i, min_loc_i, max_loc_i = cv2.minMaxLoc(result_ichi)
				if max_val_i >= 0.8 and max_val_z >= 0.8 and playable == True :
					combo = 50; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo50 = True
			if combo100 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[2], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[0], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True :
					combo = 100; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo100 = True
			if combo200 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[3], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[1], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True :
					combo = 200; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo200 = True
			if combo300 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[4], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[2], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True :
					combo = 300; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo300 = True
			if combo400 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[5], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[3], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True :
					combo = 400; playable = False
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo400 = True
			if combo500 == False :
				result_zyuu = cv2.matchTemplate(zyuu_img, zyuu_imgs[7], cv2.TM_CCOEFF_NORMED)
				result_hyaku = cv2.matchTemplate(hyaku_img, hyaku_imgs[6], cv2.TM_CCOEFF_NORMED)
				min_val_z, max_val_z, min_loc_z, max_loc_z = cv2.minMaxLoc(result_zyuu)
				l_v_h, h_v_h, l_l_h, h_l_h = cv2.minMaxLoc(result_hyaku)
				if max_val_z >= 0.8 and h_v_h >= 0.8 and playable == True :
					combo = 500; playable = False; comboend = True
					if detected == True and name != "_" :
						se_thread = threading.Thread(target=mod_music_play(combo, name))
						se_thread.start()
					else :
						m_s_thread = threading.Thread(target=thread_music_play(combo, muse, aqours))
						m_s_thread.start()
					combo500 = True
			if k & 0xFF == ord('b') or comboend == True :
				dt = set_time_table(first_time, dt)
				time = dt.strftime("%M:%S")
				clip_temp = difficulty + " " + time
				pyperclip.copy(clip_temp)
				
				#booleans initializer
				analyzable = False; combo = 0; combo10 = False
				combo50 = False; combo100 = False; combo200 = False
				combo300 = False; combo400 = False; combo500 = False
				comboend = False
				if character_detectable == True :
					character_detectable = False; name = "_"
				if detected == True :
					character_detectable = False; name = "_"; detected = False
				if name != "_" :
					name = "_"
		if k & 0xFF == ord('q') or k & 0xFF == 27 :
			if debug == True :
				cv2.imwrite("face.png",face_img)
				#cv2.imwrite("onpu.png",onpu_img)
				cv2.imwrite("frame_result.png", frame)
			break
	else :
		break

#以下はキャプチャーの解放処理と、ウィンドウの削除処理.
cv2.destroyAllWindows()
cap.release()

#"""
pyperclip.copy(clip)
