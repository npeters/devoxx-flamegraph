package sample.flamegraph.tools;

import java.io.IOException;

/**
 * perf stat -r5 -e dTLB-load-misses,dTLB-loads,L1-dcache-load-misses,L1-dcache-loads,cycles,cache-misses,bus-cycles,iTLB-load-misses,iTLB-loads  -- java -Xmx6g sample.flamegraph.tools.CacheMiss 1
 *
 *  sudo perf record -g -e dTLB-load-misses  -- java -Xmx6g sample.flamegraph.tools.CacheMiss 0
 *
 *
 *
 *
 * ➜  main git:(wip) ✗ perf stat -r1 -e major-faults,minor-faults,page-faults,dTLB-load-misses,dTLB-loads,L1-dcache-load-misses,L1-dcache-loads,cycles,cache-misses,bus-cycles,iTLB-load-misses,iTLB-loads,LLC-loads,LLC-prefetches  -- java -Xmx8g sample.flamegraph.tools.CacheMiss 1
 App pid:13402
 slowCompute: 208574349312
 3079


 Performance counter stats for 'java -Xmx8g sample.flamegraph.tools.CacheMiss 1':

 0      major-faults
 4 293      minor-faults
 4 294      page-faults
 289 880 133      dTLB-load-misses          #   67,68% of all dTLB cache hits   (36,44%)
 428 307 665      dTLB-loads                                                    (36,31%)
 382 213 275      L1-dcache-load-misses     #   81,58% of all L1-dcache hits    (18,21%)
 468 494 226      L1-dcache-loads                                               (18,11%)
 9 009 806 878      cycles                                                        (27,18%)
 67 063 241      cache-misses                                                  (27,59%)
 363 062 098      bus-cycles                                                    (27,93%)
 149 818      iTLB-load-misses          #   71,33% of all iTLB cache hits   (37,03%)
 210 040      iTLB-loads                                                    (37,03%)
 297 265 989      LLC-loads                                                     (36,59%)
 306 107 903      LLC-prefetches                                                (36,24%)

 4,462055158 seconds time elapsed

 ➜  main git:(wip) ✗ perf stat -r1 -e major-faults,minor-faults,page-faults,dTLB-load-misses,dTLB-loads,L1-dcache-load-misses,L1-dcache-loads,cycles,cache-misses,bus-cycles,iTLB-load-misses,iTLB-loads,LLC-loads,LLC-prefetches  -- java -Xmx8g sample.flamegraph.tools.CacheMiss 0
 App pid:13433
 fastCompute: 208574349312
 140


 Performance counter stats for 'java -Xmx8g sample.flamegraph.tools.CacheMiss 0':

 0      major-faults
 4 421      minor-faults
 4 422      page-faults
 303 976      dTLB-load-misses          #    0,06% of all dTLB cache hits   (39,80%)
 514 622 013      dTLB-loads                                                    (36,63%)
 143 017 904      L1-dcache-load-misses     #   34,04% of all L1-dcache hits    (34,10%)
 420 088 295      L1-dcache-loads                                               (18,48%)
 2 092 814 481      cycles                                                        (27,55%)
 53 981 869      cache-misses                                                  (18,67%)
 72 588 134      bus-cycles                                                    (27,85%)
 66 119      iTLB-load-misses          #   16,45% of all iTLB cache hits   (36,93%)
 401 971      iTLB-loads                                                    (36,72%)
 38 684 951      LLC-loads                                                     (37,76%)
 28 956 436      LLC-prefetches                                                (38,78%)

 1,641123094 seconds time elapsed

 ➜  main git:(wip) ✗

 */
public class CacheMiss {
    private static final int LONG_SIZE = 8;
    private static final int PAGE_SIZE = 2 * 1024 * 1024;
    private static final int ONE_GIG = 1024 * 1024 * 1024;
    private static final long TWO_GIG = 2L * ONE_GIG;

    private static final int ARRAY_SIZE = (int) (TWO_GIG / LONG_SIZE);
    private static final int WORDS_PER_PAGE = PAGE_SIZE / LONG_SIZE;

    private static final int ARRAY_MASK = ARRAY_SIZE - 1;
    private static final int PAGE_MASK = WORDS_PER_PAGE - 1;

    private static final int PRIME_INC = 514229;

    private static final long[] memory = new long[ARRAY_SIZE];

    private static final int NB_PAGE = 100;

    // private static final long[][] array = new long[NB_PAGE][PAGE_SIZE];


    static {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            memory[i] = 777;
        }
    }

    public void slowCompute() {
        long start = System.currentTimeMillis();
        long result = 0;
        for (int j = 0; j < PAGE_SIZE; j++) {
            for (int i = 0; i < ARRAY_SIZE; i = i + PAGE_SIZE) {
                result += memory[i + j];
            }
        }
        System.out.println("slowCompute: "+result);
        System.out.println(System.currentTimeMillis() - start);
    }

    public void fastCompute(){
        long start = System.currentTimeMillis();
        long result = 0;

        for (int i = 0; i < ARRAY_SIZE; i = i + PAGE_SIZE) {
            for (int j = 0; j < PAGE_SIZE; j++) {
                result += memory[i + j];
            }
        }
        System.out.println("fastCompute: "+result);
        System.out.println(System.currentTimeMillis() - start);

    }

    public static void main(String[] args) throws Exception {


        System.out.println("App pid:" + GCLoad.getPid());

        if (args[0].equals("0")) {
           new CacheMiss().fastCompute();
        } else {
            new CacheMiss().slowCompute();
        }

        System.in.read();

  /*      long start = System.currentTimeMillis();
        long result = 0;
        for (int j=0;j< NB_PAGE;j++){
            for (int i=0;i< PAGE_SIZE;i++){
                result +=  array[j][i] ;
            }
        }
        System.out.println("result:"+result);
        System.out.println(System.currentTimeMillis()-start);
*/


    }
}
