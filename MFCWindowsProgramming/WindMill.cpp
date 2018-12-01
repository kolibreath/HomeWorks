/*-----------------------------------------------------
   SCRNSIZE.C -- Displays screen size in a message box
				 (c) Charles Petzold, 1998
  -----------------------------------------------------*/

#include <windows.h>
#include <tchar.h>     
#include <stdio.h>
#include <math.h>
#define PI 3.1415926

double alpha = 0.0;

LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);

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
	static int  cxChar, cxCaps, cyChar, cyClient, iVscrollPos;
	HDC         hdc;
	int         i, y;
	PAINTSTRUCT ps;
	TCHAR       szBuffer[10];
	TEXTMETRIC  tm;
	RECT clientRect;
	HPEN hpen;
	HBRUSH hbrush;

	//风车的半径 和 中心坐标 
	int xOrg, yOrg, rWinmill;

	switch (message)
	{
	case WM_CREATE:
		SetTimer(hwnd, 9999, 100, NULL);
		break;


	case WM_PAINT:
		hdc = BeginPaint(hwnd, &ps);
		GetClientRect(hwnd, &clientRect);
		hpen = (HPEN)GetStockObject(BLACK_PEN);
		hbrush = CreateSolidBrush(RGB(255, 220, 220));

		SelectObject(hdc, hpen);
		SelectObject(hdc, hbrush);

		xOrg = (clientRect.left + clientRect.right) / 2;
		yOrg = (clientRect.top + clientRect.bottom) / 2;

        //每次遍历增加i度
        alpha ++;

		rWinmill = min(xOrg, yOrg) - 50;
		int rectLeftTopX = xOrg - rWinmill, rectLeftTopY = yOrg - rWinmill, rectBottomX = xOrg + rWinmill, rectBottomY = yOrg + rWinmill;

		hbrush = CreateSolidBrush(RGB(200, 255, 200));
		SelectObject(hdc, hbrush);

		// 当 alpha等于0 起始的形状类似于 奔驰的标志
		// 从原点(xOrg,yOrg) ->  (aEndX,aEndY) 是一条边
		// 从原点(xOrg,yOrg) ->  (bEndX,bEndY) 是一条边
		// 从原点(xOrg,yOrg) ->  (cEndX,cEndY) 是一条边
		//三条边的夹角是120度

        int aEndX = xOrg + rWinmill * sin((alpha )/180*PI );
        int aEndY = yOrg + rWinmill * cos((alpha )/ 180*PI);

        int bEndX = xOrg + rWinmill * sin((alpha + 120 )/180*PI );
        int bEndY = yOrg + rWinmill * cos((alpha + 120 )/ 180*PI);

        int cEndX = xOrg + rWinmill * sin((alpha + 240 )/180*PI );
        int cEndY = yOrg + rWinmill * cos((alpha + 240 )/ 180*PI);

        int startX = xOrg;
        int startY = yOrg;

		//绘制饼图
		Pie(hdc,
			rectLeftTopX, rectLeftTopY,
			rectBottomX, rectBottomY,
			aEndX, aEndY,
		    bEndX, bEndY);
        //修改颜色绘制后续的饼图
        hbrush = CreateSolidBrush(RGB(255, 255, 200));
		SelectObject(hdc, hbrush);

       Pie(hdc,
			rectLeftTopX, rectLeftTopY,
			rectBottomX, rectBottomY,					    
            bEndX, bEndY,
            cEndX, cEndY);

        hbrush = CreateSolidBrush(RGB(200, 255, 255));
		SelectObject(hdc, hbrush);
		
        Pie(hdc,
			rectLeftTopX, rectLeftTopY,
			rectBottomX, rectBottomY,
			cEndX, cEndY,
		    aEndX, aEndY);


	case WM_TIMER:
		if (wParam == 9999) {
			InvalidateRect(hwnd, NULL, TRUE);
		}

	}
	return DefWindowProc(hwnd, message, wParam, lParam);
}
