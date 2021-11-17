def rotate90(matrix):
    ret = []
    new_matrix_row = 0
    for i in reversed(range(len(matrix[0]))):
        ret.append([])
        for j in range(len(matrix)):
            ret[new_matrix_row].append(matrix[j][i])
        new_matrix_row += 1
    return ret

def find_pattern(matrix, pattern):
    coords = []
    for i in range(len(matrix) - len(pattern) + 1):
        for j in range(len(matrix[0]) - len(pattern[0]) + 1):
            sub_mat = get_sub_matrix(matrix, i, j, len(pattern), len(pattern[0]))
            if(sub_mat == pattern):
                 coords.append([i,j])
    return coords

def get_sub_matrix(matrix, start_row, start_column, rowspan, columnspan):
    ret = []
    for i in range(start_row, start_row + rowspan):
        ret.append([])
        for j in range(start_column, start_column + columnspan):
            ret[i - start_row].append(matrix[i][j])
    return ret;

