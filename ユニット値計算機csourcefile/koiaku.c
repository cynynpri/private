#include <stdio.h>
#include <stdlib.h>
#include <math.h>//floor��ceil���g��

int main( int argc, char *argv[])
{
	printf("�J�[�h�X���̃f�[�^���烆�j�b�g�l���v�Z���܂��B\n");
	printf("�J�[�h��SIS�����̑����l����͂��Ă��������B(9����)\n");
	int properties[9] = {0};
	int len = 0;
	
	for(len = 0;len < 9;len++)
	{
		printf("%d�Ԗڂ̃J�[�h:",len+1);
		scanf("%d",&properties[len]);
	}
	
	printf("���ɑ������Ă���L�b�X��p�t���[���̐�����͂��܂��B\n");
	int kissies[9] = {0};
	int perfumes[9] = {0};
	int aura = 0;
	int veil = 0;
	int rings[9] = {0};
	int crossies[9] = {0};
	int tricks[9] = {0};
	
	for(len = 0;len < 9;len++)
	{
		printf("%d�Ԗڂ̃J�[�h�̑������Ă���L�b�X�̐�(�������0):", len+1);
		scanf("%d",&kissies[len]);
		printf("%d�Ԗڂ̃J�[�h�̑������Ă���p�t���[���̐�(�������0):", len+1);
		scanf("%d", &perfumes[len]);
		printf("%d�Ԗڂ̃J�[�h�̑������Ă��郊���O�̐�(�������0):", len+1);
		scanf("%d", &rings[len]);
		printf("%d�Ԗڂ̃J�[�h�̑������Ă���N���X�̐�(�������0):", len+1);
		scanf("%d", &crossies[len]);
		printf("%d�Ԗڂ̃J�[�h�̑������Ă���g���b�N�̐�(�������0):", len+1);
		scanf("%d", &tricks[len]);
		printf("\n\n\n");
	}
	printf("\n");
	printf("���Ƀ��j�b�g�ɂ��Ă���I�[���ƃ��F�[���̑�������͂��Ă��������B\n");
	printf("�I�[��:");
	scanf("%d",&aura);
	printf("���F�[��:");
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
	
	printf("���ɃZ���^�[�X�L���A�T�u�Z���^�[�X�L���ɂ��A�b�v���v�Z���܂��B\n");
	printf("�t�����h�͗L��Ōv�Z���܂����H");
	printf("�L��Ȃ��1�𖳂��Ȃ��0����͂��Ă��������B\n");
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
		printf("�Z���^�[�X�L���̏㏸�l����͂��Ă��������B�Ⴆ��9%�A�b�v����Ȃ�9�Ȃ�\n");
		scanf("%d", &cenup);
		if(cenup == 0){
			printf("���l������������܂���B\n");
			system("@pause");
			return 0;
		}
		dcu = cenup /100.0;
		printf("���ɃT�u�Z���^�[�X�L���̏㏸�l����͂��Ă��������B6%�A�b�v�Ȃ�6�Ȃ�\n");
		scanf("%d", &subcu);
		dscu = subcu / 100.0;
		for(len = 0;len < 9;len++)
		{
			printf("%d�Ԗڂ̃J�[�h�̓T�u�Z���^�[�̑Ώۂł����H�Ώۂł����1�������łȂ����0����͂��Ă��������B\n", len+1);
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
		printf("���j�b�g�l:%d+%d\n", unitsm, sumcu);
		printf("1�^�b�v�X�R�A��,\n");
		double dscr = 0;
		dscr = floor(unitsm/80.0);
		int iniscr = (int)dscr;
		int secscr = (int)(floor(dscr*1.1));
		int finscr = (int)(floor(dscr*1.1*1.1));
		printf("%d, %d(1.1�{),%d(1.21�{)\n",iniscr, secscr, finscr);
		if(clctrbl != 0)
		{
			trunitsm += trsumcu;
			int initr = (int)(floor(trunitsm/80.0));
			int sectr = (int)(floor((trunitsm/80.0)*1.1));
			int fintr = (int)(floor((trunitsm/80.0)*1.1*1.1));
			printf("���苭����������1�^�b�v�X�R�A��,\n");
			printf("%d, %d(1.1�{),%d(1.21�{)\n",initr, sectr, fintr);
		}
		system("@pause");
		return 0;
	}
	else if(frbl == 1)
	{
		printf("�����j�b�g�̃Z���^�[�X�L���̏㏸�l����͂��Ă��������B�Ⴆ��9%�A�b�v����Ȃ�9�Ȃ�\n");
		scanf("%d", &cenup);
		if(cenup == 0){
			printf("���l������������܂���B\n");
			system("@pause");
			return 0;
		}
		dcu = cenup /100.0;
		printf("���Ɏ����j�b�g�̃T�u�Z���^�[�X�L���̏㏸�l����͂��Ă��������B6%�A�b�v�Ȃ�6�Ȃ�\n");
		scanf("%d", &subcu);
		dscu = subcu / 100.0;
		for(len = 0;len < 9;len++)
		{
			printf("%d�Ԗڂ̃J�[�h�̓T�u�Z���^�[�̑Ώۂł����H�Ώۂł����1�������łȂ����0����͂��Ă��������B\n", len+1);
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
		printf("�t�����h�̏��������܂��B\n");
		printf("�t�����h�̃Z���^�[�X�L���̏㏸�l����͂��Ă��������B9% -> 9\n");
		int frcu = 0;
		scanf("%d", &frcu);
		if(frcu == 0){
			printf("���l������������܂���B\n");
			system("@pause");
			return 0;
		}
		printf("�t�����h�̃T�u�Z���^�[�X�L���̏㏸�l����͂��Ă��������B6% -> 6\n");
		int frscu = 0;
		scanf("%d", &frscu);
		for(len = 0;len < 9;len++)
		{
			printf("%d�Ԗڂ̃J�[�h�̓t�����h�̃T�u�Z���^�[�̑Ώۂł����H�Ώۂł����1�������łȂ����0����͂��Ă��������B\n", len+1);
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
		printf("���j�b�g�l:%d+%d\n", unitsm, sumcu);
		printf("1�^�b�v�X�R�A��,\n");
		double dscr = 0;
		dscr = floor(unitsm/80.0);
		int iniscr = (int)dscr;
		int secscr = (int)(floor(dscr*1.1));
		int finscr = (int)(floor(dscr*1.1*1.1));
		printf("%d, %d(1.1�{),%d(1.21�{)\n",iniscr, secscr, finscr);
		if(clctrbl != 0)
		{
			trunitsm += trsumcu;
			int initr = (int)(floor(trunitsm/80.0));
			int sectr = (int)(floor((trunitsm/80.0)*1.1));
			int fintr = (int)(floor((trunitsm/80.0)*1.1*1.1));
			printf("���苭����������1�^�b�v�X�R�A��,\n");
			printf("%d, %d(1.1�{),%d(1.21�{)\n",initr, sectr, fintr);
		}
		system("@pause");
		return 0;
	}
	else
	{
		printf("�w�肳�ꂽ���l�𐳊m�ɓ��͂��Ă��������B\n");
		system("@pause");
		return 0;
	}
}