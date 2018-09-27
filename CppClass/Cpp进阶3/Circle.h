#ifndef __CIRCLE__
#define __CIRCLE__


#include "Graph.h"

class CCircle:virtual public CGraph{

private :
    Point Pt;
    int Radiu;

public:
    CCircle(Point & pt, int radiu =0,int color=0,int linewidth=1);
    CCircle(CCircle& circle);
	CCircle(int color=0,int linewidth=1):CGraph(1,color,linewidth){};
    ~CCircle(){};
    virtual void showMessage();

    void saveP(fstream& file);
    void save(fstream &file);

	void load(fstream &file);
	

};
#endif // __CIRCLE__
