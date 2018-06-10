#include <iostream>
#include <fstream>
#include "Graph.h"
#include "Rect.h"
#include "Circle.h"
#include "CircleRect.h"
#include "CPage.h"
#include "CMotion.h"
using namespace std;


int main()
{   
    Point point1(3,4);

    CRect* rect = new CRect(point1,1,3,4,5);
   // rect.ShowMsg();
    CCircle* circle = new CCircle(point1,3,4,5);
   // circle.ShowMsg();
    CCircleRect* circleRect = new CCircleRect(*rect,*circle,2,3);
   // circleRect.ShowMsg();

    CPage page;
        page.AddGraphs(rect);
        page.AddGraphs(circle);
        page.AddGraphs(circleRect);

	cout<<"Page Created:"<<endl;
	page.ShowGraphs();

	fstream file ;
	file.open("record.dat",ios::in|ios::out|ios::binary|ios::trunc);
	page.Save(file);
	file.close();

	cout<<"Page Saved:........"<<endl;

	CPage page1;
	file.open("record.dat",ios::in|ios::binary);
	page1.Load(file);
	file.close();

	cout<<"Page1, Loaded:........"<<endl;
	page1.ShowGraphs();

	CRect rect1;
	CMotion cMotion ;
	CPage cpage;
	cMotion.slideOut(page1);
    return 0;
}
