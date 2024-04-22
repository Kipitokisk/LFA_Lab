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
        # Find occurrences of empty string
        nt_epsilon = []
        for key, value in self.P.items():
            s = key
            productions = value
            for p in productions:
                if p == 'eps':
                    nt_epsilon.append(s)

        for key, value in self.P.items():
            new_productions = []
            for ep in nt_epsilon:
                for v in value:
                    prod_copy = v
                    if ep in prod_copy:
                        # Generate all possible combinations without epsilon
                        new_productions.extend([prod_copy.replace(c, '') for c in prod_copy if c != ep])
            value.extend(new_productions)

        # Remove epsilon from the productions
        P1 = self.P.copy()
        for key, value in self.P.items():
            P1[key] = [v for v in value if v != 'eps']

        # Remove non-productive symbols
        P_final = {key: value for key, value in P1.items() if value}
        self.P = P_final.copy()

        print(f"1. No epsilon productions:\n{P_final}")
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
        temp = {}
        new_symbol_counter = 0

        # Generate new symbols for CNF productions
        def generate_new_symbol():
            nonlocal new_symbol_counter
            new_symbol_counter += 1
            return f'X{new_symbol_counter}'

        for key, value in self.P.items():
            new_productions = []
            for production in value:
                while len(production) > 2:
                    new_symbol = generate_new_symbol()
                    temp[new_symbol] = production[:2]
                    new_productions.append(new_symbol)
                    production = production[2:]
                new_productions.append(production)
            self.P[key] = new_productions

        # Remove epsilon productions
        for key, value in self.P.items():
            self.P[key] = [v for v in value if v != '']

        # Add new symbols to the grammar
        self.P.update(temp)

        print(f"5. Final Grammar:\n{self.P}")

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