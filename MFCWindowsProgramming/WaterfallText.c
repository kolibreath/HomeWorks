/*--------------------------------------------------------
KEYVIEW1.C -- Displays Keyboard and Character Messages
				(c) Charles Petzold, 1998
--------------------------------------------------------*/
#include <windows.h>
#include <tchar.h>     
#include <stdio.h>
#include<stdio.h>
#include<stdlib.h>

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);

//确定当前的行数
int curIndex = 0;


//todo 字体生成函数
HFONT NewFont(HDC hdc, int nCharHeight) {

	TCHAR *textBuf[] = { L"宋体",L"楷体" ,L"Arial" };
	HFONT hfont;
	hfont = CreateFont(
		nCharHeight,
		0,
		0,
		0,
		400,
		rand() % 2,
		0,
		0,
		ANSI_CHARSET,
		OUT_DEFAULT_PRECIS,
		CLIP_DEFAULT_PRECIS,
		DEFAULT_QUALITY,
		DEFAULT_PITCH | FF_DONTCARE,
		textBuf[curIndex % 3]
	);

	if (hfont == NULL)
		return hfont;
	else
		return hfont;
}

// red green blue
int red = 100, green = 100, blue = 100;

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
	PSTR szCmdLine, int iCmdShow)
{
	static TCHAR szAppName[] = TEXT("SysMets2");
	HWND         hwnd;
	MSG          msg;
	WNDCLASS     wndclass;

	wndclass.style = CS_HREDRAW | CS_VREDRAW;
	wndclass.lpfnWndProc = WndProc;
	wndclass.cbClsExtra = 0;
	wndclass.cbWndExtra = 0;
	wndclass.hInstance = hInstance;
	wndclass.hIcon = LoadIcon(NULL, IDI_APPLICATION);
	wndclass.hCursor = LoadCursor(NULL, IDC_ARROW);
	wndclass.hbrBackground = (HBRUSH)GetStockObject(WHITE_BRUSH);
	wndclass.lpszMenuName = NULL;
	wndclass.lpszClassName = szAppName;

	if (!RegisterClass(&wndclass))
	{
		MessageBox(NULL, TEXT("This program requires Windows NT!"),
			szAppName, MB_ICONERROR);
		return 0;
	}

	hwnd = CreateWindow(szAppName, TEXT("Get System Metrics No. 2"),
		WS_OVERLAPPEDWINDOW | WS_VSCROLL,
		CW_USEDEFAULT, CW_USEDEFAULT,
		CW_USEDEFAULT, CW_USEDEFAULT,
		NULL, NULL, hInstance, NULL);

	ShowWindow(hwnd, iCmdShow);
	UpdateWindow(hwnd);

	while (GetMessage(&msg, NULL, 0, 0))
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}
	return msg.wParam;
}



LRESULT CALLBACK WndProc(HWND hwnd, UINT message, WPARAM wParam, LPARAM lParam)
{

	HDC hdc;
	TEXTMETRIC tm;
	static long nXChar, nYChar;
	static TCHAR *textBuf[] = {
		L"富强 民主",
		L"文明 和谐 公正",
		L"平等 自由 博爱 法制",
		L"爱国 敬业 诚信 友善 奉献",
		L"社会 主义 核心 价值 观念 yeah"
	};

	//求数组市镇textBuf 的长度
	int lines = 5;
	//求数组中每一个字体的长度
	int numberHash[5];
	for (int i = 0; i < 5; i++) {
		numberHash[i] = lstrlen(textBuf[i]);
		printf("the length %d\n", lstrlen(textBuf[i]));
	}

	//长度和数组的长度对应
	PAINTSTRUCT ptStr;
	switch (message) {
	case  WM_CREATE:
		hdc = GetDC(hwnd);
		//todo 实现自定义字体   
		//获取文字的tm 字体
		//如果没有这一行的话容易出错
		GetTextMetrics(hdc, &tm);
		nXChar = tm.tmAveCharWidth;
		nYChar = tm.tmHeight + tm.tmExternalLeading;
		ReleaseDC(hwnd, hdc);
		SetTimer(hwnd, 999, 1000, NULL);
		break;

	case WM_PAINT:
		//开始绘画并且给ptStr赋值
		hdc = BeginPaint(hwnd, &ptStr);

		int size = 10;
		int x = 0, y = 0;
		//curX: 每一行起始的x j++的时候会+20 表示这一行的这个数字 向右移动20
		int curX = 0, curY = 0;

		int i = curIndex % lines;
		y = 50 * i;
		size = 20 * i;

		for (int j = 0; j < numberHash[i]; j++) {

			HFONT font = NewFont(hdc, rand() % 40);
			SelectObject(hdc, font);
			GetTextMetrics(hdc, &tm);

			//生成当前的RGB 
			curX += 50;
			curY += 2;

			red = (red + rand()) % 255;
			green = (green + rand()) % 255;
			blue = (blue + rand()) % 255;

			SetTextColor(hdc, RGB(red, green, blue));
			TextOut(hdc, x + curX, y + curY, &textBuf[i][j], 1);
		}
		//每次循环结束之后会向下x移动30像素


		EndPaint(hwnd, &ptStr);

	case WM_TIMER:
		if (wParam == 999) {
			curIndex++;
			InvalidateRect(hwnd, NULL, TRUE);
		}
	}

	return DefWindowProc(hwnd, message, wParam, lParam);
}
