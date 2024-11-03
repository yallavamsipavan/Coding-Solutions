class Solution {

    int M = 1000000007;
    int totalSum;
    int totalEvenSpace;
    int totalOddSpace;
    int[] freqArr;
    int[] prefSum;
    long[] factArr;
    long[] revFactArr;
    long[][][] memo;
    String num;
    
    public int countBalancedPermutations(String num) {
        freqArr = new int[10];
        this.num = num;
        totalSum = 0;
        for(char ch: num.toCharArray()){
            freqArr[ch - '0']++;
            totalSum += (ch - '0');
        }
        totalEvenSpace = totalOddSpace = num.length() / 2;
        if((num.length() % 2) == 1) totalEvenSpace++;
        prefSum = new int[11];
        prefSum[0] = 0;
        for(int i = 1; i <= 10; i++){
            prefSum[i] = freqArr[i - 1] + prefSum[i - 1];
        }
        factArr = new long[num.length() + 1];
        revFactArr = new long[num.length() + 1];
        factArr[0] = 1;
        revFactArr[0] = 1;
        for(int i = 1; i <= num.length(); i++){
            factArr[i] = (factArr[i - 1] * i) % M;
            revFactArr[i] = fastPower(factArr[i], M - 2);
        }
        memo = new long[10][totalSum + 1][num.length()];
        for(long[][] arr: memo){
            for(long[] arr1: arr){
                Arrays.fill(arr1, -1);
            }
        }
        return (int) dp(0, 0, 0);    
    }

    private long dp(int num, int evenSum, int evenLen){
        if(num == 10){
            if(evenSum == (totalSum - evenSum)) return 1;
            return 0;
        }
        if(memo[num][evenSum][evenLen] == -1){
            int count = freqArr[num];
            int evenSpaces = totalEvenSpace - evenLen;
            int oddSpaces = totalOddSpace - (prefSum[num] - evenLen);
            long ans = 0;
            for(int i = 0; i <= Math.min(count, evenSpaces); i++){
                if(count - i > oddSpaces) continue;
                ans = (ans + (((nCr(evenSpaces, i) * nCr(oddSpaces, count - i)) % M) * dp(num + 1, evenSum + (num * i), evenLen + i)) % M) % M;
            }
            memo[num][evenSum][evenLen] = ans;
        }
        return memo[num][evenSum][evenLen];
    }

    private long nCr(int n, int r){
        return (((factArr[n] * revFactArr[n - r]) % M) * revFactArr[r]) % M;
    }

    private long fastPower(long a, long b){
        if(b == 0) return 1;
        long halfPower = fastPower(a, b / 2);
        if((b % 2) == 0){
            return (halfPower * halfPower) % M;
        }
        return (a * ((halfPower * halfPower) % M)) % M;
    }
}
