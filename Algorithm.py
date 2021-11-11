#TODO
#completare la rotazione con le matrici con altezza e lunghezza differenti 
def delete_space(p1):
    if (isinstance(p1,list)):
        pattern = []
        apn = 0
        for i in range(len(p1)):
            if (p1[i] != ''):
                pattern.append([])
                for j in range(len(p1[i])):
                    if (p1[i][j] == '1' or p1[i][j] =='0' and (p1[i][j] != '\t' or p1[i][j] != ' ')):
                        if (p1[i][j] == '1' or p1[i][j] =='0'):
                            pattern[apn].append(p1[i][j])
                        else: 
                            raise TypeError("Wrong type")
                apn+=1            
        return pattern
    else:
        raise TypeError("Wrong type")

def correct_length(p):
    l = len(p)
    r = 0 
    v = True
    while (r < l and v == True):
        if len(p[0]) == len(p[r]):
             v = True
             r +=1
        else: 
            v = False
    return v

def rotate90(matrix):
    ret = []
    new_matrix_row = 0
    for i in reversed(range(len(matrix[0]))):
        ret.append([])
        for j in range(len(matrix)):
            ret[new_matrix_row].append(matrix[j][i])
        new_matrix_row += 1
    return ret

#metodo per effettuare le rotazioni del pattern di 90° 180° e 270°
def rotate(p, degrees):
    pr = []
    h = len(p)
    l = len(p[0])
    if h == l:
        if degrees == 90:
            for i in range(h):
                pr.append([])
                for j in range(l):
                    pr[i].append(p[j][i])
            return pr
        elif degrees == 180: 
            pr = rotate(p, 90)
            pr1 = rotate(pr, 90)
            return pr1
        else:
            if degrees == 270:
                pr = rotate(p, 90)
                pr1 = rotate(pr, 90)
                pr2 = rotate(pr1, 90)
                return pr2
            raise ValueError(" Invalid rotation value")
    else: 
        if degrees == 90:
            for i in range(h):
                pr.append([])
                for j in range(l):
                    pr[i].append(p[i][j])
            return pr
        elif degrees == 180: 
            pr = rotate(p, 90)
            pr1 = rotate(pr, 90)
            return pr1
        else:
            if degrees == 270:
                pr = rotate(p, 90)
                pr1 = rotate(pr, 90)
                pr2 = rotate(pr1, 90)
                return pr2
            raise ValueError(" Invalid rotation value")

#matrix_path -> stringa del file contenente la matrice
#pattern -> matrice di '0' e '1'
def find_pattern(matrix_path, pattern):
    var_matr = delete_space(open(matrix_path).read().splitlines())
    higthm = len(var_matr)
    lengthm = len(var_matr[0])
    higthp = len(pattern)
    lengthp = len(pattern[0])
    l = 0
    h = 0
    n = 0
    equals = True 
    coordinates = []
    if(correct_length(pattern) == True ):
        if(higthm >= higthp and lengthm >= lengthp):
            if(higthm == higthp):
                if(lengthm == lengthp):
                    matrix=[]
                    for i2 in range(higthp):
                        matrix.append([])
                        for j2 in range(lengthp):
                            matrix[i2].append(var_matr[i2][j2])
                    if(matrix == pattern):
                        n = n+1
                        coordinates.append([0, 0])
                #altezza uguale e lunghezza diversa 
                else:
                    for k in range(lengthm-lengthp+1):
                        matrix=[]
                        for i in range(higthp):
                            matrix.append([])
                            l=k
                            for j in range(lengthp):
                                matrix[i].append(var_matr[i][l])
                                l=l+1
                        if(pattern == matrix):
                            n = n+1   
                            coordinates.append([0, k])
            else:
                #altezza diversa e lunghezza uguale
                if(lengthm==lengthp):
                    for w in range(higthm-higthp+1):
                        h=w
                        matrix=[]
                        for i in range(higthp):
                            matrix.append([])
                            for j in range(lengthp):
                                matrix[i].append(var_matr[h][j])
                            h=h+1
                        if(pattern == matrix):
                            n = n+1 
                            coordinates.append([w, 0])
                #tutto diverso 
                else: 
                    for w in range(higthm-higthp+1):
                        for k in range(lengthm-lengthp+1):
                            matrix=[]
                            for i in range(higthp):
                                matrix.append([])
                                l=k
                                for j in range(lengthp):
                                    matrix[i].append(var_matr[h][l])
                                    l=l+1
                                h=h+1
                            h=w
                            if(pattern == matrix):
                                n=n+1
                                coordinates.append([w, k])
            return n, coordinates
        else: raise ValueError("Dimensions of the matrix are smaller than pattern's")
    else: raise IndexError("Pattern line have different sizes")

