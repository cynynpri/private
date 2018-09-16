/**
 *
 */
package cynynpri.java.sif.combose.detects.Card;

/**
 * @author game
 *
 */
public class Card {
	protected enum CardName{
		Honoka("高坂穂乃果"),
		Eli("絢瀬絵里"),
		Kotori("南ことり"),
		Umi("園田海未"),
		Rin("星空凛"),
		Maki("西木野真姫"),
		Nozomi("東條希"),
		Hanayo("小泉花陽"),
		Nico("矢澤にこ"),
		Chika("高海千歌"),
		Riko("桜内梨子"),
		Kanan("松浦果南"),
		Dia("黒澤ダイヤ"),
		You("渡辺曜"),
		Yoshiko("津島善子"),
		Hanamaru("国木田花丸"),
		Mari("小原鞠莉"),
		Ruby("黒澤ルビィ");
		private String charaName;

		private CardName(String charaName) {
			this.charaName = charaName;
		}

		public String getName() {
			return this.charaName;
		}
	}
	private CardName charaName;
	private CardAttribute attr;
	private CardSkill skill;
}
