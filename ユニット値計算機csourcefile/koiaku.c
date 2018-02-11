#include <stdio.h>
#include <stdlib.h>
#include <math.h>//floorやceilを使う

int main( int argc, char *argv[])
{
	printf("カード９枚のデータからユニット値を計算します。\n");
	printf("カードのSIS抜きの属性値を入力してください。(9枚分)\n");
	int properties[9] = {0};
	int len = 0;
	
	for(len = 0;len < 9;len++)
	{
		printf("%d番目のカード:",len+1);
		scanf("%d",&properties[len]);
	}
	
	printf("次に装着しているキッスやパフュームの数を入力します。\n");
	int kissies[9] = {0};
	int perfumes[9] = {0};
	int aura = 0;
	int veil = 0;
	int rings[9] = {0};
	int crossies[9] = {0};
	int tricks[9] = {0};
	
	for(len = 0;len < 9;len++)
	{
		printf("%d番目のカードの装着しているキッスの数(無ければ0):", len+1);
		scanf("%d",&kissies[len]);
		printf("%d番目のカードの装着しているパフュームの数(無ければ0):", len+1);
		scanf("%d", &perfumes[len]);
		printf("%d番目のカードの装着しているリングの数(無ければ0):", len+1);
		scanf("%d", &rings[len]);
		printf("%d番目のカードの装着しているクロスの数(無ければ0):", len+1);
		scanf("%d", &crossies[len]);
		printf("%d番目のカードの装着しているトリックの数(無ければ0):", len+1);
		scanf("%d", &tricks[len]);
		printf("\n\n\n");
	}
	printf("\n");
	printf("次にユニットについているオーラとヴェールの総数を入力してください。\n");
	printf("オーラ:");
	scanf("%d",&aura);
	printf("ヴェール:");
	scanf("%d", &veil);
	
	for(len = 0;len < 9;len++)
	{
		properties[len] += 200 * kissies[len];
		properties[len] += 450 * perfumes[len];
	}
	
	int pprtys[9] = {0};
	int trpprtys[9] = {0};
	int tra = 0;
	int trv = 0;
	if(veil != 0)
	{
		trv = 1;
	}
	if(aura != 0)
	{
		tra = 1;
	}
	
	for(len = 0;len < 9;len++)
	{
		pprtys[len] = properties[len];
		pprtys[len] += (int)(ceil(properties[len]*0.10*rings[len])+ceil(properties[len]*0.16*crossies[len])+ceil(properties[len]*0.024)*veil+ceil(properties[len]*0.018)*aura);
		if(tricks[len] != 0)
		{
			trpprtys[len] = properties[len];
			trpprtys[len] += (int)(ceil(properties[len]*0.33*(1.024*trv)*(1.018*tra)));
		}
		else
		{
			trpprtys[len] = pprtys[len];
		}
	}
	
	printf("次にセンタースキル、サブセンタースキルによるアップを計算します。\n");
	printf("フレンドは有りで計算しますか？");
	printf("有りならば1を無しならば0を入力してください。\n");
	int frbl = 0;
	scanf("%d", &frbl);
	int cenup = 0;
	double dcu = 0.0;
	int subcu = 0;
	double dscu = 0.0;
	int clctrbl = 0;
	int sumcu = 0;
	int trsumcu = 0;
	int unitsm = 0;
	int trunitsm = 0;
	for(len = 0;len < 9;len++)
	{
		if(tricks[len] != 0)
		{
			clctrbl = 1;
		}
	}
	if(frbl == 0)
	{
		printf("センタースキルの上昇値を入力してください。例えば9%アップするなら9など\n");
		scanf("%d", &cenup);
		if(cenup == 0){
			printf("数値が正しくありません。\n");
			system("@pause");
			return 0;
		}
		dcu = cenup /100.0;
		printf("次にサブセンタースキルの上昇値を入力してください。6%アップなら6など\n");
		scanf("%d", &subcu);
		dscu = subcu / 100.0;
		for(len = 0;len < 9;len++)
		{
			printf("%d番目のカードはサブセンターの対象ですか？対象であれば1をそうでなければ0を入力してください。\n", len+1);
			printf("Yes -> 1 or No -> 0:");
			int tbl = 0;
			scanf("%d", &tbl);
			if(tbl == 0)
			{
				sumcu += (int)(ceil(pprtys[len]*dcu));
				if(clctrbl != 0)
				{
					trsumcu += (int)(ceil(trpprtys[len]*dcu));
					trunitsm += trpprtys[len];
				}
			}
			else
			{
				sumcu += (int)(ceil(pprtys[len]*dcu)+ceil(pprtys[len]*dscu));
				if(clctrbl != 0)
				{
					trsumcu += (int)(ceil(trpprtys[len]*dcu)+ceil(trpprtys[len]*dscu));
					trunitsm += trpprtys[len];
				}
			}
			unitsm += pprtys[len];
			printf("\n");
		}
		unitsm += sumcu;
		printf("ユニット値:%d+%d\n", unitsm, sumcu);
		printf("1タップスコアは,\n");
		double dscr = 0;
		dscr = floor(unitsm/80.0);
		int iniscr = (int)dscr;
		int secscr = (int)(floor(dscr*1.1));
		int finscr = (int)(floor(dscr*1.1*1.1));
		printf("%d, %d(1.1倍),%d(1.21倍)\n",iniscr, secscr, finscr);
		if(clctrbl != 0)
		{
			trunitsm += trsumcu;
			int initr = (int)(floor(trunitsm/80.0));
			int sectr = (int)(floor((trunitsm/80.0)*1.1));
			int fintr = (int)(floor((trunitsm/80.0)*1.1*1.1));
			printf("判定強化発動時の1タップスコアは,\n");
			printf("%d, %d(1.1倍),%d(1.21倍)\n",initr, sectr, fintr);
		}
		system("@pause");
		return 0;
	}
	else if(frbl == 1)
	{
		printf("自ユニットのセンタースキルの上昇値を入力してください。例えば9%アップするなら9など\n");
		scanf("%d", &cenup);
		if(cenup == 0){
			printf("数値が正しくありません。\n");
			system("@pause");
			return 0;
		}
		dcu = cenup /100.0;
		printf("次に自ユニットのサブセンタースキルの上昇値を入力してください。6%アップなら6など\n");
		scanf("%d", &subcu);
		dscu = subcu / 100.0;
		for(len = 0;len < 9;len++)
		{
			printf("%d番目のカードはサブセンターの対象ですか？対象であれば1をそうでなければ0を入力してください。\n", len+1);
			printf("Yes -> 1 or No -> 0:");
			int tbl = 0;
			scanf("%d", &tbl);
			if(tbl == 0)
			{
				sumcu += (int)(ceil(pprtys[len]*dcu));
				if(clctrbl != 0)
				{
					trsumcu += (int)(ceil(trpprtys[len]*dcu));
					trunitsm += trpprtys[len];
				}
			}
			else
			{
				sumcu += (int)(ceil(pprtys[len]*dcu)+ceil(pprtys[len]*dscu));
				if(clctrbl != 0)
				{
					trsumcu += (int)(ceil(trpprtys[len]*dcu)+ceil(trpprtys[len]*dscu));
					trunitsm += trpprtys[len];
				}
			}
			unitsm += pprtys[len];
			printf("\n");
		}
		printf("フレンドの処理をします。\n");
		printf("フレンドのセンタースキルの上昇値を入力してください。9% -> 9\n");
		int frcu = 0;
		scanf("%d", &frcu);
		if(frcu == 0){
			printf("数値が正しくありません。\n");
			system("@pause");
			return 0;
		}
		printf("フレンドのサブセンタースキルの上昇値を入力してください。6% -> 6\n");
		int frscu = 0;
		scanf("%d", &frscu);
		for(len = 0;len < 9;len++)
		{
			printf("%d番目のカードはフレンドのサブセンターの対象ですか？対象であれば1をそうでなければ0を入力してください。\n", len+1);
			printf("Yes -> 1 or No -> 0:");
			int tbl = 0;
			scanf("%d", &tbl);
			if(tbl == 0)
			{
				sumcu += (int)(ceil(pprtys[len]*frcu/100.0));
				if(clctrbl != 0)
				{
					trsumcu += (int)(ceil(trpprtys[len]*frcu/100.0));
				}
			}
			else
			{
				sumcu += (int)(ceil(pprtys[len]*frcu/100.0)+ceil(pprtys[len]*frscu/100.0));
				if(clctrbl != 0)
				{
					trsumcu += (int)(ceil(trpprtys[len]*frcu/100.0)+ceil(trpprtys[len]*frscu/100.0));
				}
			}
			printf("\n");
		}
		unitsm += sumcu;
		printf("ユニット値:%d+%d\n", unitsm, sumcu);
		printf("1タップスコアは,\n");
		double dscr = 0;
		dscr = floor(unitsm/80.0);
		int iniscr = (int)dscr;
		int secscr = (int)(floor(dscr*1.1));
		int finscr = (int)(floor(dscr*1.1*1.1));
		printf("%d, %d(1.1倍),%d(1.21倍)\n",iniscr, secscr, finscr);
		if(clctrbl != 0)
		{
			trunitsm += trsumcu;
			int initr = (int)(floor(trunitsm/80.0));
			int sectr = (int)(floor((trunitsm/80.0)*1.1));
			int fintr = (int)(floor((trunitsm/80.0)*1.1*1.1));
			printf("判定強化発動時の1タップスコアは,\n");
			printf("%d, %d(1.1倍),%d(1.21倍)\n",initr, sectr, fintr);
		}
		system("@pause");
		return 0;
	}
	else
	{
		printf("指定された数値を正確に入力してください。\n");
		system("@pause");
		return 0;
	}
}