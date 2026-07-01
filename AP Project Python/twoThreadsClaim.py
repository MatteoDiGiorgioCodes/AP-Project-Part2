import statistics as stat
import time
import functools
import threading

def bench(_func=None, n_threads=1, seq_iter=1, iter=1):

    
    def decorator_bench(func):
        @functools.wraps(func)
        def wrapper_timer(*args, **kwargs):
            run_times = []

            def wrapper_repeat():
                for i in range(seq_iter):
                    func(*args, **kwargs)

            for x in range(iter):
                threads = []
                for y in range(n_threads):
                    threads.append(threading.Thread(None, target=wrapper_repeat))
                start_time = time.perf_counter()
                for t in threads:
                    t.start()
                for t in threads:
                    t.join()
                end_time = time.perf_counter()
                run_time = end_time - start_time
                run_times.append(run_time)
            return {'fun': func.__name__, 'args': (args, kwargs), 'n_threads': n_threads, 'seq_iter': seq_iter, 'iter':iter, 'mean':stat.mean(run_times), 'variance':stat.variance(run_times)}
        return wrapper_timer

    if _func is None:
            return decorator_bench
    else:
        return decorator_bench(_func)
    
def test(fun, iter, *args):
    
    for i in range(4):
        file = open(fun.__name__+'_'+", ".join(map(str, args))+'_'+str(pow(2,i))+'_'+str(pow(2,4-i))+'.txt', 'w')
        f=bench(_func=fun, n_threads=pow(2,i), seq_iter=pow(2,4-i), iter=iter)
        file.write(str(f(*args))+'\n')
    file.close()

#Sotto sono riportati i risultati della funzione test applicata alla funzioni just-wait e grezzo, descritte nella consegna dal progetto, 
#applicate rispettivamente a 2 e 4, entrambe con valore di iter 10000
#
#{'fun': 'just_wait', 'args': ((2,), {}), 'n_threads': 1, 'seq_iter': 16, 'iter': 10000, 'mean': 0.504561128860549, 'variance': 7.192779210910418e-05}
#{'fun': 'just_wait', 'args': ((2,), {}), 'n_threads': 2, 'seq_iter': 8, 'iter': 10000, 'mean': 0.2521782041303464, 'variance': 2.7347021120538553e-05}
#{'fun': 'just_wait', 'args': ((2,), {}), 'n_threads': 4, 'seq_iter': 4, 'iter': 10000, 'mean': 0.1263128923806129, 'variance': 1.2904118284363712e-05}
#{'fun': 'just_wait', 'args': ((2,), {}), 'n_threads': 8, 'seq_iter': 2, 'iter': 10000, 'mean': 0.06337739839982241, 'variance': 8.16886655172892e-06}
#
#{'fun': 'grezzo', 'args': ((4,), {}), 'n_threads': 1, 'seq_iter': 16, 'iter': 10000, 'mean': 0.00013936374991899356, 'variance': 2.0002722784201673e-09}
#{'fun': 'grezzo', 'args': ((4,), {}), 'n_threads': 2, 'seq_iter': 8, 'iter': 10000, 'mean': 0.00025630544058512895, 'variance': 9.909859678576836e-10}
#{'fun': 'grezzo', 'args': ((4,), {}), 'n_threads': 4, 'seq_iter': 4, 'iter': 10000, 'mean': 0.000527446489862632, 'variance': 1.1006749475319899e-07}
#{'fun': 'grezzo', 'args': ((4,), {}), 'n_threads': 8, 'seq_iter': 2, 'iter': 10000, 'mean': 0.0010204114905791356, 'variance': 2.4071274176494745e-08}
#
#La teoria e la documentazione ci diconoe che a causa della GIL(Global Interpreter Lock), solo un thread Python può eseguire bytecode per volta, 
#rendendo i programmi paralleli, in realtà sequenziali, e se si vuole ottenere un vero parallelismo in Python, è necessario ricorrere al multiprocessing.
#I test eseguiti su queste 2 funzioni ci permettono di evidenziare quando può essere utile ricorrere al multithreading.
#
#Nella prima funzione quando viene chiamata la funzione sleep il thread lascia la GIL, e se ci sono altri thread in attesa, questi possono eseguire
#il loro codice mentre il primo dorme, portando il programma a una situazione dove dividendo lo stesso lavoro tra più thread l'efficenza scala
#come previsto, dato che con un singolo thread le sleep verrebbero svolte in sequenza, con l'avvio della seconda che deve attendere la terminazione
#della prima, quindi in questo caso il multithreading porta a un aumento di efficienza.
#
#Nel secondo caso invece la funzione grezzo svolge un compito costante, che fa si che il thread che la esegue non rilasci la GUI fino alla terminazione.
#In questo caso si ha quindi che tutte le esecuzioni di grezzo verranno comunque svolte in sequenza, e il tentativo di parallelizzare il programma
#porta solo a una perdita di efficienza a causa dell'overhead introdotto per gestire il passaggio del controllo da un thread a un altro.
#
#In conclusione, scrivere programmi multi-threaded in Python può facilmente rivelarsi contro producente, e lo sviluppatore deva sapere che non
#verrà mai eseguito codice in parallelo senza ricorrere al multi-processing, ma per programmi in cui i vari thread devono eseguire delle attese
#di qualche genere, il multithreading permette che in questi "momenti morti" venga eseguito il codice di un altro thread.