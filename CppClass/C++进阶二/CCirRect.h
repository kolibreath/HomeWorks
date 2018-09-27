#pragma once
#include"CCircle.h"
#include"CRect.h"
#include"CGraph.h"

class CirRect : public Circle,public Rect{
public:
	string type;
	string color;
	int linewidth;
	point pos;

	CirRect();
	CirRect(string stype);
	void show();
	void setCirRect();

	string getColor();
	string getType();
	point getPoint();

	int getRadius();

};