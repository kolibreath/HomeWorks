#include "CCircle.h"

Circle::Circle(string stype) {
	type = stype;
}

Circle::Circle() {}

Circle::Circle(int r)
{
	radius = r;
}

void Circle::setCircle()
{
	setGraph();
	int r;
	cout << "please input the radius of graph" << endl;
	cin >> r;
	radius = r;
}

void Circle::show()
{
	string s_type = type;
	cout << "the line width of the " <<s_type<<" is "<< linewidth << endl;
	cout << "the color of the "<<s_type <<" is " << color << endl;
	cout << "the position is (" << pos.x << "," << pos.y << ")" << endl;
	cout << "the radius of the "<<s_type <<" is " << radius << endl;
}

point Circle::getPoint() {
    return pos;
}
