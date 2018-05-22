#include "CGraph.h"

CGraph::CGraph()
{
}



void CGraph::setGraph()
{
	cout << "please input the line width:" << endl;
	cin >> linewidth;
	cout << "please input the color" << endl;
	cin >> color;
	cout << "please input the position" << endl;
	cin >> pos.x >> pos.y;
}
