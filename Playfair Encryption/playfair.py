# Gregory Walsh
# Cryptography Fall 2018 
# Programming Assignment 1 
# Created 10/1/18


from collections import OrderedDict
import string
import fileinput




def cleanText(text):
    """ Function will "Clean" text by splitting, removing punctation then joining """
    #Split By Whitespace 
    text = text.split()
    #Remove Punctuation
    table = str.maketrans('', '', string.punctuation)
    stripped = [w.translate(table) for w in text]
    stripped = [text.upper() for text in stripped]
    stripped = ''.join(stripped)
    return stripped


def formatText(plain_text):
    """ Function will remove duplicate letters, "clean" text given from file,
     check if text is odd to add an 'X' at the end, and seperate letters into
     groups of two """
    #Clean text and append into list
    plain_text = cleanText(plain_text)
    list = []
    for i in plain_text:
        list.append(i)
    #Check if text is odd and add 'X' if needed
    if len(list) % 2 == 1:
        list.append('X')

    #Remove 2nd letter in duplicated and replace with 'X'
    j = 0
    for i in range(len(list)//2):
        if list[j] == list[j+1]:
            list.insert(j+1,'X')
        j=j+2
    

    k = 0 
    final = []
    for letter in range(1,len(list)//2 + 1):
        final.append(list[k:k+2])
        k=k+2

    return(final)

def seperateCipher(cipher_text):
    """ Function will seperate the Cipher text into groups of 2 for decrypting """
    i = 0
    final = []
    for x in range(len(cipher_text)//2):
        final.append(cipher_text[i:i+2])
        i = i + 2
    return final

def createMatrix(key):
    """ Function will create a Matrix using a Key given by user """

    alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ"
    matrix_inside = []
    matrix_outside = []
    key = cleanText(key)
    for i in key:
        if i not in matrix_inside:
            matrix_inside.append(i)
    
    for i in alphabet:
        if i not in matrix_inside:
            matrix_inside.append(i)

    for i in range(5):
        matrix_outside.append('')

    matrix_outside[0] = matrix_inside[0:5]
    matrix_outside[1] = matrix_inside[5:10]
    matrix_outside[2] = matrix_inside[10:15]
    matrix_outside[3] = matrix_inside[15:20]
    matrix_outside[4] = matrix_inside[20:25]
    
    return matrix_outside

def getPosition(matrix,letter):
    """ Function will search through the matrix and find and X,Y value pair """
    letter = letter.upper()
    x=0
    y=0
    for i in range(5):
        for j in range(5):
            if matrix[i][j] == letter:
                x=i
                y=j

    return x,y



def encrypt(plain_text):
    """ Function will encrypt plain_text using a key from user. 
        Uses createMatrix, fromatText, and getPosition to format
        and encrypt the text """

    message = formatText(plain_text)
    matrix = createMatrix(key)
    cipher_text = []
    for i in message:
        row1,col1 = getPosition(matrix,i[0])
        row2,col2 = getPosition(matrix,i[1])
        if row1 == row2:
            if col1 == 4:
                col1 =- 1
            if col2 == 4:
                col2 =- 1
            cipher_text.append(matrix[row1][col1 + 1])
            cipher_text.append(matrix[row1][col2 + 1])		
        elif col1 == col2:
            if row1 == 4:
                row1 =- 1
            if row2 == 4:
                row2 =- 1
            cipher_text.append(matrix[row1 + 1][col1])
            cipher_text.append(matrix[row2 + 1][col2])
        else:
            #Swap
            cipher_text.append(matrix[row1][col2])
            cipher_text.append(matrix[row2][col1])
            

    return cipher_text

def decrypt(cipher_text):
    """ Function will decrypt a cipher_text file using the key from the user.
        Uses seperateCipher, createMatrix, getPosition """

    text = seperateCipher(cipher_text)
    matrix = createMatrix(key)
    plain_text = []
    for i in text:
        row1,col1=getPosition(matrix, i[0])
        row2,col2=getPosition(matrix, i[1])
        if row1 == row2:
            if col1 == 4:
                col1 =- 1
            if col2 == 4:
                col2 =- 1
            plain_text.append(matrix[row1][col1 - 1])
            plain_text.append(matrix[row1][col2 - 1])		
        elif col1 == col2:
            if row1 == 4:
                row1 =- 1
            if row2 == 4:
                row2 =- 1
            plain_text.append(matrix[row1 -1][col1])
            plain_text.append(matrix[row2 -1][col2])
        else:
            #Swap
            plain_text.append(matrix[row1][col2])
            plain_text.append(matrix[row2][col1])
            

    # This is optional to remove X's from Plaintext after decrypted 

    #for item in range(len(plain_text)):
    #    if "X" in plain_text:
    #        plain_text.remove("X")
    
    decrypted_text = plain_text
    return decrypted_text


# BEGIN USER INTERACTION

choice = input("Encrpyt(1) or Decrypt(2): ")
print(choice)
key = input("Please enter the Key: ")
key = str(key)
newlist = []
final = ''


if choice == '1':
    filename = "plaintext.txt"
    with open(filename) as f:
        for line in f:
            list = f.read()
            newlist.append(line)
    f.close()
    
    print("Your Choice is to Encrypt with the Key: " + key)
    newlist = str(newlist)
    print("The Plain Text from file is: " + newlist)
    plain_text = newlist
    matrix = createMatrix(key)
    print("This is the generated matrix from the keyword given:\n" + str(matrix))
    final = encrypt(plain_text)
    with open("out1.txt" , "w") as f:
        f.write(str(''.join(final)))
    f.close()
    print("Please check file out1.txt for Cipher Text")
            

elif choice == '2':
    filename = "ciphertext.txt"
    with open(filename) as f:
        for line in f:
            list = f.read()
            newlist.append(line)
    f.close()
    print("Your Choice is to Decrpyt with the Key: " + key)
    newlist = str(newlist)
    cipher_text = newlist
    matrix = createMatrix(key)
    print("This is the generated matrix from the keyword given:\n"+str(matrix))
    final = decrypt(cipher_text)
    with open("out2.txt" , "w") as f:
        f.write(str(''.join(final)))
    f.close()
    print("Please check file out2.txt for Plain Text")

else:
    print("Sorry your selection is invalid please try again!")



    


# THIS IS USED FOR DEBUGGING PURPOSES. IGNORE. 
if __name__ == "__main__":
   # x = getText()
    #y = cleanText(x)
    #z = removeDup("aabbccdd")
    #print(z)
    #y = cleanText("HeLo$%^adjxiASSDSDF")
    #s = createMatrix('Hello')
    #print(y)
    #y = formatText(y)
    #print(y)
    #print(s)
    #print(getPosition(s,'x'))

    #x = encrypt("HELPGETMEOUT")7
    #z = decrypt("KVAWCOKOQCIVZN")
    #print(x)
    #print(z)
    #x = playfairMain()
    pass