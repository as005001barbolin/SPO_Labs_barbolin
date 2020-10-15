#include <stdio.h>
#include <windows.h>
#include <time.h>
#include <string>
#include <iostream>
#include <fstream>
#include <math.h>

using namespace std;


#pragma pack(2)

//��������� ����� BMP 
typedef struct tBITMAPFILEHEADER
{
	WORD bfType;
	DWORD bfSize;
	WORD bfReserved1;
	WORD bfReserved2;
	DWORD bfOffBits;
}sFileHead;

//��������� BitMap's
typedef struct tBITMAPINFOHEADER
{
	DWORD biSize;
	LONG biWidth;
	LONG biHeight;
	WORD biPlanes;
	WORD biBitCount;
	DWORD biCompression;
	DWORD biSizeImage;
	LONG biXPelsPerMeter;
	LONG biYPelsPerMeter;
	DWORD biClrUsed;
	DWORD biClrImportant;
}sInfoHead;

sFileHead FileHead;
sInfoHead InfoHead;

//�������
struct Color
{
	BYTE red;
	BYTE green;
	BYTE blue;
};

//������ 1-�� �������
int pixel_size = sizeof(Color);


//1 - BMP, 2 - CMP
int img_type = 0;

//�������� �����������
Color *src_image = 0;
//�������������� �����������
Color *dst_image = 0;

//������ �����������
int width = 0;
int height = 0;

//������� ��������� BMP �����
void ShowBMPHeaders(tBITMAPFILEHEADER fh, tBITMAPINFOHEADER ih)
{
	cout << "Type: " << (CHAR)fh.bfType << endl;
	cout << "Size: " << fh.bfSize << endl;
	cout << "Shift of bits: " << fh.bfOffBits << endl;
	cout << "Width: " << ih.biWidth << endl;
	cout << "Height: " << ih.biHeight << endl;
	cout << "Planes: " << ih.biPlanes << endl;
	cout << "BitCount: " << ih.biBitCount << endl;
	cout << "Compression: " << ih.biCompression << endl;
}

//������� ��� �������� �����������
bool OpenImage(string path)
{
	ifstream img_file;
	Color temp;
	char buf[3];

	//������� ���� �� ������
	img_file.open(path.c_str(), ios::in | ios::binary);
	if (!img_file)
	{
		cout << "File isn`t open!" << endl;
		return false;
	}

	//������� ��������� BMP
	img_file.read((char*)&FileHead, sizeof(FileHead));
	img_file.read((char*)&InfoHead, sizeof(InfoHead));

	img_type = 1;
	ShowBMPHeaders(FileHead, InfoHead);
	//��������� ����� � ������ �����������
	width = InfoHead.biWidth;
	height = InfoHead.biHeight;


	//�������� ����� ��� �����������
	src_image = new Color[width*height];

	int i, j;
	for (i = 0; i < height; i++)
	{
		for (j = 0; j < width; j++)
		{
			img_file.read((char*)&temp, pixel_size);
			src_image[i*width + j] = temp;
		}
		//�������� ���� ������������ ��� ������������ �� �������� �����
		img_file.read((char*)buf, j % 4);
	}
	img_file.close();

	return true;
}

//������� ��� ���������� �����������
bool SaveImage(string path)
{
	ofstream img_file;
	char buf[3];

	//������� ���� �� ������
	img_file.open(path.c_str(), ios::out | ios::binary);
	if (!img_file)
	{
		return false;
	}

	img_file.write((char*)&FileHead, sizeof(FileHead));
	img_file.write((char*)&InfoHead, sizeof(InfoHead));

	//����������� �� ��������� � �������������� �����������
	/*if (dst_image == 0)
	{*/
		dst_image = new Color[width*height];
		memcpy(dst_image, src_image, width*height * sizeof(Color));
	/*}*/

	//�������� ����
	int i, j;
	for (i = 0; i < height; i++)
	{
		for (j = 0; j < width; j++)
		{
			img_file.write((char*)&dst_image[i*width + j], pixel_size);
		}
		img_file.write((char*)buf, j % 4);
	}
	img_file.close();

	return true;
}

//����������� ���������� ��������������� ����������� � ���������
void CopyDstToSrc()
{
	if (dst_image != 0)
	{
		memcpy(src_image, dst_image, width*height * sizeof(Color));
	}
}

//���������� ����������� � �������� ����� �����������
void AddNoise(double probability)
{
	int size = width * height;
	int count = (int)(size*probability) / 100;
	int x, y;
	long pos;
	for (int i = 0; i < count; i++)
	{
		x = rand() % width;
		y = rand() % height;
		pos = y * width + x;
		src_image[pos].blue = rand()%256;
		src_image[pos].green = rand() % 256;
		src_image[pos].red = rand() % 256;
	}
	cout << "Point was added: " << count << endl;
}
//������� 1 - �������������� ������
void Task1() 
{
	int mask[3][3] = { { 1,1,1 },
	{ 1,2,1 },
	{ 1,1,1 } };
	long pos, pointer;
	int R = 0, G = 0, B = 0;
	//������ 2 ����� ���������� �� ����������� �� �����������
	for (int j = 1; j < height-1; j++)
	{
		for (int i = 1; i < width-1; i++)
		{
			pos = j*width + i;
			//2 ���������� �� ����������� �� ����� 3�3
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 3; l++) {
					pointer = width * (j - 1 + k) + (i - 1 + l);
					R += src_image[pointer].red * mask[k][l];
					G += src_image[pointer].green * mask[k][l];
					B += src_image[pointer].blue * mask[k][l];
				}
			}
			src_image[pos].red = R/10;
			src_image[pos].green = G/10;
			src_image[pos].blue = B/10;
			R = 0; G = 0; B = 0;
		}
	}
}

//task2 0,299*R 0,587*G 0,114*B - �������� �����
void Task2(int porog) 
{
	int size = width * height;
	//��� ����������� ������������ � �������� ��������
	long pos, pointer;
	//������ �������� � ������ ��� �������� �� ���� 3�3
	double* F = new double[size];
	double* Fnew = new double[size];
	double* buf_array = new double[8];
	/*��� ��� ���� �������� "Y[i]" - ����� ������������� ��������, �� ��� ����������� ������ �����
	����� ���������� ������������ ������� � ���� ���������, �� ������� ����� ��� Y */
	double max = 0, Y;
	double S, T; 

	//������� �������
	for (int j = 0; j < size; j++)
	{
		F[j] = 0.299 * src_image[j].red + 0.587 * src_image[j].green + 0.114 * src_image[j].blue;
		Fnew[j] = F[j];
	}

	//���������� ���������
	for (int j = 1; j < height - 1; j++)
	{
		for (int i = 1; i < width - 1; i++)
		{
			/*�������������� ����� ����������� ������� � �������� ������ �������,
			����������� �������� ������ ������������ ������� */
			pos = j * width + i;
			int key = 0;
			for (int k = 0; k < 3; k++) 
			{
				for (int l = 0; l < 3; l++) 
				{
					//�������� ���� 3�3 � ����������� ��������. 
					//����������� ������� � ������ �� ����������
					pointer = width * (j - 1 + k) + (i - 1 + l);
					if (pointer != pos) {
						buf_array[key++] = F[pointer];
					}
				}
			}
			/*������ � ��� ���� ������ buf_array, �������� 8 ���������, ���������� �������,
			������ ������������. �������� ���������� S � T, ����� ��������� ��������� Y[i].
			�������� ������������ S, T � Y[i], ��� ����������� ����� �������. */
			for (int z = 0; z < 8; z++)
			{
				S = 0; T = 0;
				for (int s = 0; s < 3; s++) {
					if (s + z >= 8) {
						S += buf_array[s + z - 8];
					}
					else S += buf_array[s + z];
				}
				for (int t = 3; t < 8; t++) {
					if (t + z >= 8) {
						T += buf_array[t + z - 8];
					}
					else T += buf_array[t + z];
				}
				//������������ Y ��� ������� ���� � ��������� ������ �� �� ������� max.
				Y = abs(3 * T - 5 * S);
				if (max <= Y) max = Y;
			}
			//�������� � Fnew ������������ �������� Y[i] (� ����� ������ max) � �������� ��� 
			Fnew[pos] = max;
			max = 0;
		}
	}
	for (int i = 0; i < size; i++) {
		if (porog >= Fnew[i]) {
			src_image[i].red = 255;
			src_image[i].green = 255;
			src_image[i].blue = 255;
		}
		else {
			src_image[i].red = 0;
			src_image[i].green = 0;
			src_image[i].blue = 0;
		}
	}
	//��������� ���������� 2-�� �������
	delete[] F;
	delete[] Fnew;
	delete[] buf_array;
}

//Task 3 - �������� ���� ����� ����������� ���� �� ������
void Task3() {
	//��������� �������� ������� ��� ��������� ����������� 
	int R, G, B;
	//���������� �������, �� ������� ���� ������ ������������� �����
	int posOfTPx, posMax;
	//���������� ���������� ����������� ������
	int countOfThisPixel, countMax = 0;
	int size = height * width;
	//����� ������� ������� ����������� �� ����������� � ������������ ���������� ����������
	for (int i = 0; i < size; i++) {
		R = src_image[i].red;
		G = src_image[i].green;
		B = src_image[i].blue;
		posOfTPx = i;
		countOfThisPixel = 0;
		for (int j = 0; j < size; j++) {
			if (R == src_image[j].red && G == src_image[j].green&& B == src_image[j].blue) {
				countOfThisPixel++;
			}
		}
		//���� ���������� ���������� ������� ����� ��������� �������, �� �� ��� �������� �� �����
		if (countOfThisPixel >= countMax) {
			countMax = countOfThisPixel;
			posMax = posOfTPx;
		}
	}
	//������, ����� �� ����� ��� ��������� ����� ����� ����������� ����, ������� ��� ��������
	//�� ������� � ������� �� � 0 �� ���� ������, ��� ����� ���� ���������� � �����������
	R = src_image[posMax].red;
	G = src_image[posMax].green;
	B = src_image[posMax].blue;
	for (int i = 0; i < size; i++) {
		if (R == src_image[i].red && G == src_image[i].green&& B == src_image[i].blue) {
			src_image[i].red = 0;
			src_image[i].green = 0;
			src_image[i].blue = 0;
		}
	}
}
//���������� ������� ����������� � ������� ������ ������������ ������������
void ShowImage(string path)
{
	ShowBMPHeaders(FileHead, InfoHead);
	system(path.c_str());
}

//������� ���� � �����������
void ReadPath(string &str)
{
	str.clear();
	cout << "Enter path to image" << endl;
	cin >> str;
}

int main(int argc, char* argv[])
{
	srand((unsigned)time(NULL));
	int porog;
	//���� � �������� �����������
	string path_to_image, temp, tempFortask2, tempFortask3;
	//���� ����� ������� �� 1-� �������
	int check = 0;
	ReadPath(path_to_image);
	OpenImage(path_to_image);
	ShowImage(path_to_image);
	AddNoise(5);
	ReadPath(temp);
	SaveImage(temp);
	ShowImage(temp);
	cout << "Do you want to see task1 results? \n1 - Yes \n2 - No" << endl;
	cin >> check;
	switch (check) {
	case 1: {
		ReadPath(path_to_image);
		OpenImage(path_to_image);
		ShowImage(path_to_image);
		AddNoise(5);
		Task1();
		ReadPath(temp);
		SaveImage(temp);
		ShowImage(temp);
	}
	case 2: break;
	default: cout << "Incorrect number. Task 1 was skipped" << endl;
	}
	//���� ����� ������� �� 2-� �������
	check = 0;
	cout << "Do you want to see task2 results? \n1 - Yes \n2 - No" << endl;
	cin >> check;
	switch (check) {
	case 1: {
		cout << "Vvedite porog" << endl; cin >> porog;
		ReadPath(path_to_image);
		OpenImage(path_to_image);
		ShowImage(path_to_image);
		Task2(porog);
		ReadPath(tempFortask2);
		SaveImage(tempFortask2);
		ShowImage(tempFortask2);
	}
	case 2: break;
	default: cout << "Incorrect number. Task 2 was skipped" << endl;
	}
	//���� ����� ������� �� 3-� �������
	check = 0;
	cout << "Do you want to see task3 results? \n1 - Yes \n2 - No" << endl;
	cin >> check;
	switch (check) {
	case 1: {
		ReadPath(path_to_image);
		OpenImage(path_to_image);
		ShowImage(path_to_image);
		Task3();
		ReadPath(tempFortask3);
		SaveImage(tempFortask3);
		ShowImage(tempFortask3);
	}
	case 2: break;
	default: cout << "Incorrect number. Task 2 was skipped" << endl;
	}
	//���������� ������ ��������� �����������
	if (src_image != 0)
	{ 
		delete[] src_image;
	}
	//���������� ������ �������������� �����������
	if (dst_image != 0)
	{
		delete[] dst_image;
	}

	return 0;
}
