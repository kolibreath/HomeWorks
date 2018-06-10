#ifndef __GRAPH__
#define __GRAPH__
#include <iostream>
#include <fstream>
using namespace std;

class Point {
public:
    int X;
    int Y;

    Point(int x=0,int y=0)     {X=x;Y=y;}
};

class CGraph{
private :
    int Type;
    int Color;
    int Linewidth;

public:
    CGraph(int type=-1,int color=0,int linewidth=1);
    CGraph(CGraph& graph);
    virtual ~CGraph(){};
    void Show();
    virtual void ShowMsg() = 0;
	virtual void Load(fstream& file) = 0;
	virtual void Save(fstream& file);

};
#endif // __GRAPH__
