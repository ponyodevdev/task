package com.task.second;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Coin {
	
	
    public static int count(int[] coins, int sum) {
        int[] dp = new int[sum + 1];
        dp[0] = 1;  // 합계가 0일 때 경우의 수는 1 (아무것도 사용하지 않는 경우)
        
        for (int coin : coins) {
            for (int j = coin; j <= sum; j++) {
                dp[j] += dp[j - coin];  // 동전 하나를 추가하는 경우의 수를 갱신
            }
        }
        
        return dp[sum];
    }
	

	// 동전 조합을 저장할 리스트
    public static void coinList(int[] coins, int sum, List<Integer> currentCoinList, List<List<Integer>> allCoinList, int start) {
        // 합이 목표 sum에 도달하면 현재 조합을 저장
        if (sum == 0) {
        	allCoinList.add(new ArrayList<>(currentCoinList));  // 조합을 결과 리스트에 추가
            return;
        }
        
        // 동전들을 차례대로 사용하면서 조합을 시도
        for (int i = start; i < coins.length; i++) {
            if (coins[i] <= sum) {
            	currentCoinList.add(coins[i]);  // 현재 동전을 추가
                coinList(coins, sum - coins[i], currentCoinList, allCoinList, i);  // 동전 i를 다시 사용할 수 있음
                currentCoinList.remove(currentCoinList.size() - 1);  // 마지막 동전 제거 
            }
        }
    }
    
	public static void main(String[] args) {
	
		// 합계와 동전 배열 입력하기
        Scanner scan = new Scanner(System.in);
        
        System.out.print("sum : ");
        int sum = scan.nextInt();  // 합계 입력받기
        
        scan.nextLine();  // nextInt() 뒤에 남은 줄 바꿈 문자 처리
        
        System.out.print("coins[] (콤마(,)로 구분하여 입력해주세요!!) : ");
        String input = scan.nextLine();  // 한 줄로 입력받기 (예: 2,5,3,6)

        String[] coinStrings = input.split(",");  // 콤마 기준으로 동전 값 나누기
        int[] coins = new int[coinStrings.length];  // 동전 배열 생성
        
        // 입력받은 동전 값들을 배열에 저장
        for (int i = 0; i < coinStrings.length; i++) {
            coins[i] = Integer.parseInt(coinStrings[i]);
        }

        // 결과를 저장할 리스트
        List<List<Integer>> allCoinList = new ArrayList<>();
        List<Integer> currentCoinList = new ArrayList<>();
        
        // 동전 조합을 구하는 함수 호출
        coinList(coins, sum, currentCoinList, allCoinList, 0);
        
        // 결과 출력
        System.out.println("가능한 조합:");
        for (List<Integer> pattern : allCoinList) {
            System.out.println(pattern);
        }
        
        // 가능한 조합의 수를 출력
        System.out.println("의 " + count(coins, sum) + "가지 솔루션이 있습니다.");
    }
	
}
