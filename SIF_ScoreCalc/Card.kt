package sif_scorecalc
//文字エンコードはUTF-8で作成します(javaでコード書く時も文字エンコードはutf-8でお願いします。)

import java.util.*

class Card( rarity:String, skill_name:String, skill_conditions_type:String,
			skill_conditions_size:Int, accuracy_time:Double, 
			card_smile:Int, card_pure:Int, card_cool:Int,
			center_skill_name:String, subcenter_skill_name:String){
	val rarity = rarity//カードのレアリティ
	val skill_name = skill_name//特技名。
	val skill_conditions_type = skill_conditions_type//スキル発動条件
	val skill_conditions_size = skill_conditions_size//スキル発動条件数値
	val accuracy_time = accuracy_time//判定強化時間(個人所有のカードであれば、実際の数値。スキルのデータであれば特技レベル1の数値.)
	val card_smile = card_smile//カードスマイル値
	val card_pure = card_pure//カードピュア値
	val card_cool = card_cool//カードクール値
	val center_skill_name = center_skill_name//センターになった時のスキル。
	val subcenter_skill_name = subcenter_skill_name//センターになった時に発動するサブセンタースキル。
}