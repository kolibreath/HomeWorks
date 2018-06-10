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
    void Show();
    virtual void ShowMsg();

    void SaveP(fstream& file);
    void Save(fstream& file);

	void Load(fstream& file);
	

};
#endif // __CIRCLE__
