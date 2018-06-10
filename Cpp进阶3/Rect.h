#ifndef __RECT__
#define __RECT__
#include <iostream>
#include <fstream>
#include "Graph.h"

class CRect:virtual public CGraph{

private :
    Point Pt;
    int Width;
    int Height;

public:
    CRect(Point & pt, int width =0, int height = 0,int color=0,int linewidth=1);
    CRect(CRect& rect);
	CRect(int color=0,int linewidth=1):CGraph(2,color,linewidth){};
    void Show();
    virtual void ShowMsg();
	
	void SaveP(fstream& file);
    void Save(fstream& file);
	void Load(fstream& file);
	

};
#endif
