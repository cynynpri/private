using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace SIFUnitCalc
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_MouseClick(object sender, MouseEventArgs e)
        {
            try {
                int cd1 = int.Parse(textBox1.Text);
                int cd2 = int.Parse(textBox2.Text);
                int cd3 = int.Parse(textBox3.Text);
                int cd4 = int.Parse(textBox4.Text);
                int cd5 = int.Parse(textBox5.Text);
                int cd6 = int.Parse(textBox6.Text);
                int cd7 = int.Parse(textBox7.Text);
                int cd8 = int.Parse(textBox8.Text);
                int cd9 = int.Parse(textBox9.Text);
                int[] propertys = new int[9];
                propertys[0] = cd1; propertys[1] = cd2; propertys[2] = cd3;
                propertys[3] = cd4; propertys[4] = cd5; propertys[5] = cd6;
                propertys[6] = cd7; propertys[7] = cd8; propertys[8] = cd9;
                int[] kiss = new int[9];
                if (checkBox1.Checked == true) kiss[0] = 1;
                if (checkBox5.Checked == true) kiss[1] = 1;
                if (checkBox6.Checked == true) kiss[2] = 1;
                if (checkBox7.Checked == true) kiss[3] = 1;
                if (checkBox8.Checked == true) kiss[4] = 1;
                if (checkBox9.Checked == true) kiss[5] = 1;
                if (checkBox10.Checked == true) kiss[6] = 1;
                if (checkBox11.Checked == true) kiss[7] = 1;
                if (checkBox12.Checked == true) kiss[8] = 1;
                int[] perfume = new int[9];
                if (checkBox2.Checked == true) perfume[0] = 1;
                if (checkBox14.Checked == true) perfume[1] = 1;
                if (checkBox15.Checked == true) perfume[2] = 1;
                if (checkBox16.Checked == true) perfume[3] = 1;
                if (checkBox17.Checked == true) perfume[4] = 1;
                if (checkBox18.Checked == true) perfume[5] = 1;
                if (checkBox19.Checked == true) perfume[6] = 1;
                if (checkBox20.Checked == true) perfume[7] = 1;
                if (checkBox21.Checked == true) perfume[8] = 1;
                int[] ring = new int[9];
                if (checkBox3.Checked == true) ring[0] = 1;
                if (checkBox22.Checked == true) ring[1] = 1;
                if (checkBox23.Checked == true) ring[2] = 1;
                if (checkBox24.Checked == true) ring[3] = 1;
                if (checkBox25.Checked == true) ring[4] = 1;
                if (checkBox26.Checked == true) ring[5] = 1;
                if (checkBox27.Checked == true) ring[6] = 1;
                if (checkBox28.Checked == true) ring[7] = 1;
                if (checkBox29.Checked == true) ring[8] = 1;
                int[] cross = new int[9];
                if (checkBox4.Checked == true) cross[0] = 1;
                if (checkBox30.Checked == true) cross[1] = 1;
                if (checkBox31.Checked == true) cross[2] = 1;
                if (checkBox32.Checked == true) cross[3] = 1;
                if (checkBox33.Checked == true) cross[4] = 1;
                if (checkBox34.Checked == true) cross[5] = 1;
                if (checkBox35.Checked == true) cross[6] = 1;
                if (checkBox36.Checked == true) cross[7] = 1;
                if (checkBox37.Checked == true) cross[8] = 1;
                int[] trick = new int[9];
                if (checkBox13.Checked == true) trick[0] = 1;
                if (checkBox38.Checked == true) trick[1] = 1;
                if (checkBox39.Checked == true) trick[2] = 1;
                if (checkBox40.Checked == true) trick[3] = 1;
                if (checkBox41.Checked == true) trick[4] = 1;
                if (checkBox42.Checked == true) trick[5] = 1;
                if (checkBox43.Checked == true) trick[6] = 1;
                if (checkBox44.Checked == true) trick[7] = 1;
                if (checkBox45.Checked == true) trick[8] = 1;
                int[] subcenup = new int[9];
                if (checkBox46.Checked == true) subcenup[0] = 1;
                if (checkBox47.Checked == true) subcenup[1] = 1;
                if (checkBox48.Checked == true) subcenup[2] = 1;
                if (checkBox49.Checked == true) subcenup[3] = 1;
                if (checkBox50.Checked == true) subcenup[4] = 1;
                if (checkBox51.Checked == true) subcenup[5] = 1;
                if (checkBox52.Checked == true) subcenup[6] = 1;
                if (checkBox53.Checked == true) subcenup[7] = 1;
                if (checkBox54.Checked == true) subcenup[8] = 1;
                int[] frsubcu = new int[9];
                if (checkBox55.Checked == true) frsubcu[0] = 1;
                if (checkBox56.Checked == true) frsubcu[1] = 1;
                if (checkBox57.Checked == true) frsubcu[2] = 1;
                if (checkBox58.Checked == true) frsubcu[3] = 1;
                if (checkBox59.Checked == true) frsubcu[4] = 1;
                if (checkBox60.Checked == true) frsubcu[5] = 1;
                if (checkBox61.Checked == true) frsubcu[6] = 1;
                if (checkBox62.Checked == true) frsubcu[7] = 1;
                if (checkBox63.Checked == true) frsubcu[8] = 1;
                int[] wink = new int[9];
                if (wink1.Checked == true) wink[0] = 1;
                if (wink2.Checked == true) wink[1] = 1;
                if (wink3.Checked == true) wink[2] = 1;
                if (wink4.Checked == true) wink[3] = 1;
                if (wink5.Checked == true) wink[4] = 1;
                if (wink6.Checked == true) wink[5] = 1;
                if (wink7.Checked == true) wink[6] = 1;
                if (wink8.Checked == true) wink[7] = 1;
                if (wink9.Checked == true) wink[8] = 1;
                int[] trill = new int[9];
                if (trill1.Checked == true) trill[0] = 1;
                if (trill2.Checked == true) trill[1] = 1;
                if (trill3.Checked == true) trill[2] = 1;
                if (trill4.Checked == true) trill[3] = 1;
                if (trill5.Checked == true) trill[4] = 1;
                if (trill6.Checked == true) trill[5] = 1;
                if (trill7.Checked == true) trill[6] = 1;
                if (trill8.Checked == true) trill[7] = 1;
                if (trill9.Checked == true) trill[8] = 1;
                int[] blooms = new int[9];
                if (bloom1.Checked == true) blooms[0] = 1;
                if (bloom2.Checked == true) blooms[1] = 1;
                if (bloom3.Checked == true) blooms[2] = 1;
                if (bloom4.Checked == true) blooms[3] = 1;
                if (bloom5.Checked == true) blooms[4] = 1;
                if (bloom6.Checked == true) blooms[5] = 1;
                if (bloom7.Checked == true) blooms[6] = 1;
                if (bloom8.Checked == true) blooms[7] = 1;
                if (bloom9.Checked == true) blooms[8] = 1;
                int[] image = new int[9];
                if (image1.Checked == true) image[0] = 1;
                if (image2.Checked == true) image[1] = 1;
                if (image3.Checked == true) image[2] = 1;
                if (image4.Checked == true) image[3] = 1;
                if (image5.Checked == true) image[4] = 1;
                if (image6.Checked == true) image[5] = 1;
                if (image7.Checked == true) image[6] = 1;
                if (image8.Checked == true) image[7] = 1;
                if (image9.Checked == true) image[8] = 1;
                int[] nonettes = new int[9];
                if (nonette1.Checked == true) nonettes[0] = 1;
                if (nonette2.Checked == true) nonettes[1] = 1;
                if (nonette3.Checked == true) nonettes[2] = 1;
                if (nonette4.Checked == true) nonettes[3] = 1;
                if (nonette5.Checked == true) nonettes[4] = 1;
                if (nonette6.Checked == true) nonettes[5] = 1;
                if (nonette7.Checked == true) nonettes[6] = 1;
                if (nonette8.Checked == true) nonettes[7] = 1;
                if (nonette9.Checked == true) nonettes[8] = 1;
                int nonette = 0;
                int bloom = 0;
                for (int len = 0; len < 9; len++)
                {
                    bloom += blooms[len];
                    nonette += nonettes[len];
                }
                int[] trpprty = new int[9];
                int aura = Decimal.ToInt32(numericUpDown5.Value);
                int veil = Decimal.ToInt32(numericUpDown6.Value);
                bool clctrbl = false;
                for (int len = 0; len < propertys.Length; len++)
                {
                    if (trick[len] != 0)
                    {
                        trpprty[len] = propertys[len];
                        trpprty[len] += (int)Math.Ceiling(propertys[len] * (0.33 * trick[len] + 0.10 * ring[len] + 0.16 * cross[len] + 0.28 * trill[len] + 0.29 * image[len]) * (1 + 0.024 * veil) * (1 + 0.018 * aura) * (1+0.04 * bloom) * (1+0.042 * nonette));
                        trpprty[len] += 200 * kiss[len];
                        trpprty[len] += 450 * perfume[len];
                        trpprty[len] += 1400 * wink[len];
                        clctrbl = true;
                    }
                    else
                    {
                        trpprty[len] += (int)Math.Ceiling(propertys[len] * 0.10 * ring[len]) + (int)Math.Ceiling(propertys[len] * 0.16 * cross[len]) + (int)Math.Ceiling(propertys[len] * 0.28 * trill[len]) +(int)Math.Ceiling(propertys[len] * 0.29 * image[len]) + (int)Math.Ceiling(propertys[len] * 0.024) * veil + (int)Math.Ceiling(propertys[len] * 0.018) * aura + (int)Math.Ceiling(propertys[len] * 0.04) * bloom  + (int)Math.Ceiling(propertys[len] * 0.042) * nonette;
                    }
                    propertys[len] += (int)Math.Ceiling(propertys[len] * 0.10 * ring[len]) + (int)Math.Ceiling(propertys[len] * 0.16 * cross[len]) + (int)Math.Ceiling(propertys[len] * 0.28 * trill[len]) + (int)Math.Ceiling(propertys[len] * 0.29 * image[len]) + (int)Math.Ceiling(propertys[len] * 0.024) * veil + (int)Math.Ceiling(propertys[len] * 0.018) * aura + (int)Math.Ceiling(propertys[len] * 0.04) * bloom + (int)Math.Ceiling(propertys[len] * 0.042) * nonette;
                    propertys[len] += 200 * kiss[len];
                    propertys[len] += 450 * perfume[len];
                    propertys[len] += 1400 * wink[len];

                }
                double dcu = 0.0;
                double dscu = 0.0;
                double frcu = 0.0;
                double frscu = 0.0;
                int unitsm = 0;
                int sumcu = 0;
                int trunitsm = 0;
                int trsumcu = 0;
                dcu = Decimal.ToInt32(numericUpDown1.Value) / 100.0;
                dscu = Decimal.ToInt32(numericUpDown2.Value) / 100.0;
                int frbl = 0;
                frbl = Decimal.ToInt32(numericUpDown3.Value);
                if (frbl == 0)
                {
                    for (int len = 0; len < propertys.Length; len++)
                    {
                        if (subcenup[len] != 0)
                        {
                            sumcu += (int)Math.Ceiling(propertys[len] * dcu) + (int)Math.Ceiling(propertys[len] * dscu);
                            if (clctrbl == true)
                            {
                                trsumcu += (int)Math.Ceiling(trpprty[len] * dcu) + (int)Math.Ceiling(trpprty[len] * dscu);
                                trunitsm += trpprty[len];
                            }
                        }
                        else
                        {
                            sumcu += (int)Math.Ceiling(propertys[len] * dcu);
                            if (clctrbl == true)
                            {
                                trsumcu += (int)Math.Ceiling(trpprty[len] * dcu);
                                trunitsm += trpprty[len];
                            }
                        }
                        unitsm += propertys[len];
                    }
                    unitsm += sumcu;
                    trunitsm += trsumcu;
                    result_lbl.Text = "ユニット値:" + unitsm + "+" + sumcu;
                    trrslt_lbl.Text = "判定強化時のユニット値:" + trunitsm;
                    return;
                }
                else
                {
                    frcu = Decimal.ToInt32(numericUpDown3.Value) / 100.0;
                    frscu = Decimal.ToInt32(numericUpDown4.Value) / 100.0;
                    for (int len = 0; len < propertys.Length; len++)
                    {
                        if (subcenup[len] != 0)
                        {
                            sumcu += (int)Math.Ceiling(propertys[len] * dcu) + (int)Math.Ceiling(propertys[len] * dscu);
                            if (clctrbl == true)
                            {
                                trsumcu += (int)Math.Ceiling(trpprty[len] * dcu) + (int)Math.Ceiling(trpprty[len] * dscu);
                                trunitsm += trpprty[len];
                            }
                        }
                        else
                        {
                            sumcu += (int)Math.Ceiling(propertys[len] * dcu);
                            if (clctrbl == true)
                            {
                                trsumcu += (int)Math.Ceiling(trpprty[len] * dcu);
                                trunitsm += trpprty[len];
                            }
                        }
                        unitsm += propertys[len];
                    }
                    for (int len = 0; len < propertys.Length; len++)
                    {
                        if (subcenup[len] != 0)
                        {
                            sumcu += (int)Math.Ceiling(propertys[len] * frcu) + (int)Math.Ceiling(propertys[len] * frscu);
                            if (clctrbl == true)
                            {
                                trsumcu += (int)Math.Ceiling(trpprty[len] * frcu) + (int)Math.Ceiling(trpprty[len] * frscu);
                            }
                        }
                        else
                        {
                            sumcu += (int)Math.Ceiling(propertys[len] * frcu);
                            if (clctrbl == true)
                            {
                                trsumcu += (int)Math.Ceiling(trpprty[len] * frcu);
                            }
                        }
                    }
                    unitsm += sumcu;
                    trunitsm += trsumcu;
                    result_lbl.Text = "ユニット値:" + unitsm + "+" + sumcu;
                    trrslt_lbl.Text = "判定強化時のユニット値:" + trunitsm;
                    return;
                }
            }
            catch (FormatException)
            {
                result_lbl.Text = "数値を入力してください。";
            }
            catch (OverflowException)
            {
                result_lbl.Text = "正しい数値を入力してください。";
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
    }
}
