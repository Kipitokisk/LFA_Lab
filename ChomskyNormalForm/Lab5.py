class Gramamr():
    def __init__(self):
        self.P = {
            'S': ['aBA', 'AB'],
            'A': ['eps', 'd', 'dS', 'AbBA'],
            'B': ['a', 'aS', 'A'],
            'D': ['Aba']
        }
        self.V_N = ['S', 'A', 'B', 'D']
        self.V_T = ['a', 'b', 'd']

    def RemoveEpsilon(self):
        # Find occurence of empty string
        nt_epsilon = []
        for key, value in self.P.items():
            s = key
            productions = value
            for p in productions:
                if p == 'eps':
                    nt_epsilon.append(s)

        for key, value in self.P.items():
            for ep in nt_epsilon:
                for v in value:
                    prod_copy = v
                    if ep in prod_copy:
                        for c in prod_copy:
                            # Delete empty string production and add new production
                            if c == ep:
                                value.append(prod_copy.replace(c, ''))
        # New copy with added production
        P1 = self.P.copy()
        # Remove empty string from copy
        for key, value in self.P.items():
            for v in value:
                if v == 'eps':
                    P1[key].remove(v)

        P_final = {}
        for key, value in P1.items():
            if len(value) != 0:
                P_final[key] = value
            else:
                self.V_N.remove(key)

        print(f"1.No epsilon productions:\n{P_final}")
        self.P = P_final.copy()
        return P_final

    def EliminateUnitProd(self):
        # New copy for next production
        P2 = self.P.copy()
        for key, value in self.P.items():
            for v in value:
                if len(v) == 1 and v in self.V_N:
                    P2[key].remove(v)
                    for p in self.P[v]:
                        P2[key].append(p)
        print(f"2.No unit productions:\n{P2}")
        self.P = P2.copy()
        return P2

    def EliminateInaccesible(self):
        P3 = self.P.copy()
        accesible_symbols = [i for i in self.V_N]
        for key, value in self.P.items():
            for v in value:
                for s in v:
                    if s in accesible_symbols:
                        accesible_symbols.remove(s)
        for el in accesible_symbols:
            del P3[el]
        print(f"3.No inaccesible productions:\n{P3}")
        print(self.V_N)
        self.P = P3.copy()
        return P3

    def RemoveUnprod(self):
        P4 = self.P.copy()

        for key, value in self.P.items():
            count = 0
            for v in value:
                for a in v:
                    if a.isupper() and a in self.V_N:
                        count += 1
                if len(v) == 1 and v in self.V_T:
                    count += 1

            if count == 0:
                del P4[key]

        # Check the values
        for key, value in self.P.items():
            for v in value:
                for c in v:
                    if c.isupper() and c not in P4.keys():
                        P4[key].remove(v)
                        break

        print(f"4. No unproductive symbols:\n{P4}")
        self.P = P4.copy()
        return P4

    def TransformToCNF(self):
        P5 = self.P.copy()
        temp = {}

        #Free symbols
        alphabet = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                      'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
        free_symbols = [v for v in alphabet if v not in self.P.keys()]
        for key, value in self.P.items():
            for v in value:

                # Production satisfies CNF
                if (len(v) == 1 and v in self.V_T) or (len(v) == 2 and v.isupper()):
                    continue
                else:

                    #Split prod in 2 parts
                    left = v[:len(v) // 2]
                    right = v[len(v) // 2:]

                    #Get new symbols
                    if left in temp.values():
                        temp_key1 = ''.join([i for i in temp.keys() if temp[i] == left])
                    else:
                        temp_key1 = free_symbols.pop(0)
                        temp[temp_key1] = left
                    if right in temp.values():
                        temp_key2 = ''.join([i for i in temp.keys() if temp[i] == right])
                    else:
                        temp_key2 = free_symbols.pop(0)
                        temp[temp_key2] = right

                    #Replace prod with new symbols
                    P5[key] = [temp_key1 + temp_key2 if item == v else item for item in P5[key]]

        #Add new productions
        for key, value in temp.items():
            P5[key] = [value]

        print(f"5. Final Grammar:\n{P5}")
        return P5

    def ReturnProductions(self):
        print(f"Initial Grammar:\n{self.P}")
        P1 = self.RemoveEpsilon()
        P2 = self.EliminateUnitProd()
        P3 = self.EliminateInaccesible()
        P4 = self.RemoveUnprod()
        P5 = self.TransformToCNF()
        return P1, P2, P3, P4, P5


if __name__ == "__main__":
    g = Gramamr()
    P1, P2, P3, P4, P5 = g.ReturnProductions()