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
   // rect.showMessage();
    CCircle* circle = new CCircle(point1,3,4,5);
   // circle.showMessage();
    CCircleRect* circleRect = new CCircleRect(*rect,*circle,2,3);
   // circleRect.showMessage();

    CPage page;
	page.insertGraphs(rect);
	page.insertGraphs(circle);
	page.insertGraphs(circleRect);

	cout<<"Page Created:"<<endl;
	page.showGraphs();

	fstream file ;
	file.open("record.dat",ios::in|ios::out|ios::binary|ios::trunc);
	page.save(file);
	file.close();

	cout<<"Page Saved:........"<<endl;

	CPage page1;
	file.open("record.dat",ios::in|ios::binary);
	page1.load(file);
	file.close();

	cout<<"Page1, Loaded:........"<<endl;
	page1.showGraphs();

	CRect rect1;
	CMotion cMotion ;

	CCircle circle1;
    CCircle circle2;
    CCircle circle3;
	page1.insertGraphs(&circle1);
	page1.insertGraphs(&circle2);
	cMotion.slideOut(page1);
    return 0;
}
