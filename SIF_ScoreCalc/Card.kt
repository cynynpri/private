package sif_scorecalc
//文字エンコードはUTF-8で作成します(javaでコード書く時も文字エンコードはutf-8でお願いします。)
//.csvファイルはすべてShift_JISコードで作成されているので、ファイルの更新が必要になります。

import java.util.*

public open class Card( rarity: String, skill_name: String, skill_conditions_type: String,
			skill_conditions_size: Int, skill_effect: String, skill_probably: Int,accuracy_time: Double,
			card_smile: Int, card_pure: Int, card_cool: Int,
			center_skill_name: String, subcenter_skill_name: String)
{
	//継承するためにpublic openに宣言を変更.
	val rarity = rarity//カードのレアリティ
	val skill_name = skill_name//特技名。
	val skill_conditions_type = skill_conditions_type//スキル発動条件
	val skill_conditions_size = skill_conditions_size//スキル発動条件数値
	val skill_effect = skill_effect//スキルの効果 e.g.)スコア,回復,判定強化,特技発動率アップ,...etc
	val skill_probably = skill_probably//特技発動確率.
	val accuracy_time = accuracy_time//判定強化時間(個人所有のカードであれば、実際の数値。スキルのデータであれば特技レベル1の数値.)
	val card_smile = card_smile//カードスマイル値
	val card_pure = card_pure//カードピュア値
	val card_cool = card_cool//カードクール値
	val center_skill_name = center_skill_name//センターになった時のスキル。
	val subcenter_skill_name = subcenter_skill_name//センターになった時に発動するサブセンタースキル。
	val attribute: String = //カードの属性 スマイル、ピュア、クール
	if(center_skill_name.indexOf("スマイル") != -1){
		"スマイル"
	}else if(center_skill_name.indexOf("ピュア") != -1){
		"ピュア"
	}else if(center_skill_name.indexOf("クール") != -1){
		"クール"
	}else{
		"Empty" //Empty is not null. Kotlin language is not available null-val.
	}
}