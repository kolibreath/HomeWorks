#include "CCirRect.h"
#include"CGraph.h"
void CirRect::show()
{
	cout << "the type of the graph is " << type << endl;
	cout << "the line width is" << linewidth << endl;
	cout << "the color of the graph is " << color << endl;
	cout << "the position is (" << pos.x << "," << pos.y << ")" << endl;
	cout << "the width of the graph" << width << endl;
	cout << "the height of the graph" << height << endl;
	cout << "the radius of the graph" << radius << endl;
}

void CirRect::setCirRect()
{
	setRect();
	cout << "please input the radius of the graph" << endl;
	int r;
	cin >> r;
	radius = r;
}

CirRect::CirRect() {

}

CirRect::CirRect(string stype) {
    this->type = stype;
}

string CirRect::getColor() {
	return color;
}

string CirRect::getType() {
	return type;
}

point CirRect::getPoint() {
	return pos;
}

int CirRect::getRadius() {
	return radius;
}