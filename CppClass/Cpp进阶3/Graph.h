#ifndef __GRAPH__
#define __GRAPH__
#include <iostream>
#include <fstream>
using namespace std;

const int    CRECT = 2;
const int    CIRCLE = 1;
const int    CIRCLERECT = 3;

class Point {
public:
    int X;
    int Y;

    Point(int x=0,int y=0)     {X=x;Y=y;}
};


class CGraph{
private :
    int Color;
    int Linewidth;

public:
    int Type;
    CGraph(int type=-1,int color=0,int linewidth=1){
            Type = type;
            Color = color;
            Linewidth = linewidth;
    }

    CGraph(CGraph& graph);

    char *getFigureDefinition(int type){
        char *figure = new char;
        switch (type){
            case CIRCLE:
                figure = "Circle";
                break;
            case CIRCLERECT:
                figure = "CircleRect";
                break;
            case CRECT:
                figure = "Rect";
                break;
        }
        return figure;
    }

    virtual ~CGraph(){};

    void show(){
        cout<<endl<<"\tType:"<<getFigureDefinition(Type) <<"\tColor:"<<Color
            <<"\tLinewidth:"<<Linewidth<<endl;
    }


    virtual void showMessage() = 0;

	virtual void load(fstream &file) = 0;

	virtual void save(fstream &file){

        file.write((char*)&Type,sizeof(Type));
        file.write((char*)&Color,sizeof(Color));
        file.write((char*)&Linewidth,sizeof(Linewidth));

    }

};
#endif // __GRAPH__
