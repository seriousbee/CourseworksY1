#include <stdio.h> 
#include <string.h>   /* for all the new-fangled string functions */
#include <stdlib.h>     /* malloc, free, rand */
#include <stdbool.h>


const int Fsize=50;
int no_edges;
int no_nodes;
int i;
const int cases=100;


int stringLength(char *g){
    int sum =0;
    while(g[sum]!='\0')
        sum++;
    return sum;
}
//take string, start index and index of element we are no longer interested in.
char* substring(char* s, int start, int end){
  int length=end-start;
  char* toReturn = malloc(length+1);
  int j=0;
  for(; j<length; j++){
    toReturn[j]=s[j+start];
  }
  toReturn[j]='\0';
  return toReturn;
}

char* substringEnd(char* s, int start){
  int length=stringLength(s)-start;
  char* toReturn = malloc(length+1);
  int i=0;
  for(; i<length; i++){
    toReturn[i]=s[i+start];
  }
  toReturn[i]='\0';
  return toReturn;
}

bool implication(bool p, bool q){
  return !p || q;
}

int getXYZ(char c, int XYZtab[]){
  return XYZtab[(c-'x')];
}

bool isXYZ(char c){
    return c=='x' || c=='y' || c=='z';
}

int XYZtoInt(char c) {
    return c-'x';
}

int* setXYZ(char c, int XYZtab[], int value){
    int* localXYZ = malloc(3);
    localXYZ[0]=XYZtab[0];
    localXYZ[1]=XYZtab[1];
    localXYZ[2]=XYZtab[2];

    localXYZ[XYZtoInt(c)] = value;
    return localXYZ;
}



bool validAtomic(char *g){
    if(stringLength(g)!=5 || g[0]!='X' || g[1]!='[' || g[4] !=']')
        return false;
    if(!isXYZ(g[2]) || !isXYZ(g[3]))
        return false;
    return true;
}

bool evaluateAtomic(char* s, int localXYZ[3], int connections[no_edges][2]){
        int j;
        for (j = 0; j < no_edges; j++) {
            if(connections[j][0]==getXYZ(s[2],localXYZ) && connections[j][1] == getXYZ(s[3],localXYZ))
                return true;
        }
        return false;
}

int parse(char *g)
{/* return 1 if atomic, 2 if  neg, 3 if binary, 4 if exists, 5 if for all, ow 0*/
  int length = stringLength(g);
    if(stringLength(g)<5){
      free(g); //NEW
      return -1;
    }
        
    
    if(length == 5 && validAtomic(g)){
      free(g); //NEW
      return 1;
    }

    if(g[0]=='-'){
            if(parse(substringEnd(g, 1))>0)
                return 2;
            else 
              return -1;
    }
    
    if (g[0] == 'E'){ //is it existential
        if(isXYZ(g[1])){
          if(parse(substringEnd(g, 2))>0)
                return 4;
          else 
              return -1;
        } else {
                return -1;
        }
    }

    if(g[0]=='A'){ //is it universal? 5
            if(isXYZ(g[1])){
                if(parse(substringEnd(g, 2))>0)
                    return 5;
                else return -1;
            } else {
                return -1;
            }
    }

    if(g[0]=='('){ //is it binary connected? 3
            if(g[length-1]==')'){
                int indentationLvl = 0, k = 0;
                for (; k < length; k++) {
                    if(g[k]=='(')
                        indentationLvl++;
                    else if (g[k]==')')
                        indentationLvl--;
                    else if(g[k]=='v' || g[k]=='>' || g[k]=='^'){
                        if(indentationLvl==1){
                            if(parse(substring(g, 1, k))>0 && parse(substring(g, k+1, length-1))>0){
                                return 3;
                            }
                            else {
                                return -1;
                            }
                        }

                    }
                }
                return -1;
            } else {
                return -1;
            }
        }
    return -1;
}

int eval(char *nm, int edges[no_edges][2], int size, int V[3])
{/* returns 1 if formla nm evaluates to true in graph with 'size' nodes, no_edges edges, edges stored in 'edges', variable assignment V.  Otherwise returns 0.*/
  int length = stringLength(nm);
  if(validAtomic(nm)){
    return evaluateAtomic(nm, V, edges);
  }
  if(nm[0]=='A'){
    int k;
        for (k = 0; k < no_nodes; k++) {
            if (!eval(substringEnd(nm, 2),edges, size, setXYZ(nm[1], V, k)))
                return false;
        }
        return true;
  }
  if(nm[0]=='E'){
    int k;
        for (k = 0; k < no_nodes; k++) {
            if (eval(substringEnd(nm, 2),edges, size, setXYZ(nm[1], V, k)))
                return true;
        }
        return false;
  }
  if(nm[0]=='('){ //TODO: can binary connected formulas look like this: (OvOvO) or only ((OvO)vO)
      int indentationLevel=0;
      int j;
      for (j = 0; j< length; j++) {
          if(nm[j]=='(')
              indentationLevel++;
          else if (nm[j]==')')
              indentationLevel--;
          else if (indentationLevel == 1){
              if (nm[j]=='v'){
                  return eval(substring(nm, 1,j), edges, size, V) || eval(substring(nm, j+1, length-1), edges, size, V);
              }
              else if (nm[j]=='^'){
                  return eval(substring(nm, 1,j), edges, size, V) && eval(substring(nm, j+1, length-1), edges, size, V);
              }
              else if (nm[j]=='>'){
                  return implication(eval(substring(nm, 1,j), edges, size, V), eval(substring(nm, j+1, length-1), edges, size, V));
              }
          }
      }
  }
  if(nm[0]=='-'){
      return !eval(substringEnd(nm, 1), edges, size, V);
  }

  
  return 0;
}



int main()
{
  char *name=malloc(Fsize); /*create space for the formula*/
  FILE *fp, *fpout;
 
  /* reads from input.txt, writes to output.txt*/
 if ((  fp=fopen("input.txt","r"))==NULL){printf("Error opening file");exit(1);}
  if ((  fpout=fopen("output.txt","w"))==NULL){printf("Error opening file");exit(1);}

  int j;
  for(j=0;j<cases;j++)
    {
      fscanf(fp, "%s %d %d",name,&no_nodes,&no_edges);/*read number of nodes, number of edges*/
      int edges[no_edges][2];  /*Store edges in 2D array*/
      for(i=0;i<no_edges;i++)	 fscanf(fp, "%d%d", &edges[i][0], &edges[i][1]);/*read all the edges*/

      /*Assign variables x, y, z to nodes */
      int W[3];
      /*Store variable values in array
	value of x in V[0]
	value of y in V[1]
	value of z in V[2] */
      for(i=0;i<3;i++)  fscanf(fp, "%d", &W[i]);
      int p=parse(name); 
      switch(p)
	{case 0:fprintf(fpout,"%s is not a formula.  ", name);break;
	case 1: fprintf(fpout,"%s is an atomic formula.  ",name);break;
	case 2: fprintf(fpout,"%s is a negated formula.  ",name);break;
	case 3: fprintf(fpout,"%s is a binary connective formula.  ", name);break;
	case 4: fprintf(fpout,"%s is an existential formula.  ",name);break;
	case 5: fprintf(fpout,"%s is a universal formula.  ",name);break;
	default: fprintf(fpout,"%s is not a formula.  ",name);break;
	}
  
      /*Now check if formula is true in the graph with given variable assignment. */
      if (p!=0){
	if (eval(name, edges, no_nodes,  W)==1)	fprintf(fpout,"The formula %s is true in G under W\n", name);
	else fprintf(fpout,"The formula %s is false in G under W\n", name);
      }
    }
 
  fclose(fp);
  fclose(fpout);
  free(name);
  return(0);
}
        

        
