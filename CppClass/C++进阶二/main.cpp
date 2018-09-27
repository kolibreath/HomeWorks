#include"CCircle.h"
#include"CCirRect.h"
#include"CGraph.h"
#include"CRect.h"
#include "CPage.h"

int main()
{
	point p;
	p.x = 3;
	p.y = 4;

	cout<<"initialize the Circle"<<endl;

	Circle circle("Circle");
	circle.setCircle();
	circle.show();

//    cout<<"initialize the Rect"<<endl;
//	Rect rect("Rect");
//    rect.setRect();
//    rect.show();
//
//    cout<<"initialize the CircleRect"<<endl;
//    CirRect cirRect("CircleRect");
//    cirRect.setCirRect();
//    cirRect.show();

	Cpage cpage(3,4);
	cpage.setCircle(circle);cpage.writePage();
	cpage.showPage();
	return 0;
}