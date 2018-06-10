#include <iostream>
#include "Graph.h"
#include "Circle.h"
using namespace std;

CCircle::CCircle(Point & pt, int radiu ,int color,int linewidth)
:CGraph( 1,color,linewidth){
     Pt = pt;
     Radiu = radiu;
}

CCircle::CCircle(CCircle & circle)
:CGraph(1){
     Pt = circle.Pt;
     Radiu = circle.Radiu;
}

 void CCircle::Show(){

    cout<<endl<<"\t Point:"<<Pt.X<<","<<Pt.Y <<" \t Radiu: "<<Radiu
       <<endl;
    }

void CCircle::ShowMsg(){

    CGraph::Show();
    Show();
}
void CCircle::SaveP(fstream& file){

    
    file.write((char*)&Pt.X,sizeof(Pt.X));
	file.write((char*)&Pt.Y,sizeof(Pt.Y));
	file.write((char*)&Radiu,sizeof(Radiu));	
}

void CCircle::Save(fstream& file){

    CGraph::Save(file);
    SaveP( file);
	
}

void CCircle::Load(fstream& file){
    
    file.read((char*)&Pt.X,sizeof(Pt.X));
	file.read((char*)&Pt.Y,sizeof(Pt.Y));
	file.read((char*)&Radiu,sizeof(Radiu));	
}
