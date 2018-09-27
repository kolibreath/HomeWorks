#include "CRect.h"

Rect::Rect() {}

Rect::Rect(string stype) {
    this->type = stype;
}

void Rect::setRect()
{
	setGraph();
	cout<<"please input the width"<<endl;
	int w;
	cin >> w;;
	width = w;
	cout << "please input the height" << endl;
	int h;
	cin >> h;
	height = h;
}

void Rect::show()
{
	cout << "the type of the graph is " << type << endl;
	cout << "the linewidth is" << linewidth << endl;
	cout << "the color of the graph is " << color << endl;
	cout << "the position is (" << pos.x << "," << pos.y << ")" << endl;
	cout << "the width of the graph" << width << endl;
	cout << "the height of the graph" << height << endl;
}

int Rect::getHeight() {return height;}

int Rect::getWidth() {return width;}
