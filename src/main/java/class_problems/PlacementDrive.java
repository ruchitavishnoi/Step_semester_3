import java.util.Scanner;

class Candidate {
    String name;
    int[] scores;
    int total;
    int bestClimb;
    int climbStart;
    int climbEnd;

    public Candidate(String name, int[] scores) {
        this.name = name;
        this.scores = scores;
        this.total = 0;
        for (int s : scores) {
            this.total += s;
        }
        calculateBestClimb();
    }

    private void calculateBestClimb() {
        int max = Integer.MIN_VALUE;
        int curr = 0;
        int s = 1;
        int tempS = 1;
        int e = 2;

        for (int i = 0; i < 5; i++) {
            int diff = scores[i + 1] - scores[i];
            
            if (curr > 0) {
                curr += diff;
            } else {
                curr = diff;
                tempS = i + 1;
            }
            
            if (curr > max) {
                max = curr;
                s = tempS;
                e = i + 2;
            }
        }
        
        this.bestClimb = max;
        this.climbStart = s;
        this.climbEnd = e;
    }
}

class Solution {
    public static void mergeSort(Candidate[] a, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(a, l, m);
            mergeSort(a, m + 1, r);
            merge(a, l, m, r);
        }
    }

    private static void merge(Candidate[] a, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        
        Candidate[] L = new Candidate[n1];
        Candidate[] R = new Candidate[n2];
        
        for (int i = 0; i < n1; i++) {
            L[i] = a[l + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = a[m + 1 + j];
        }
        
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].total >= R[j].total) {
                a[k++] = L[i++];
            } else {
                a[k++] = R[j++];
            }
        }
        
        while (i < n1) {
            a[k++] = L[i++];
        }
        while (j < n2) {
            a[k++] = R[j++];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        
        int n = scanner.nextInt();
        Candidate[] candidates = new Candidate[n];
        
        for (int i = 0; i < n; i++) {
            String name = scanner.next();
            int[] scores = new int[6];
            for (int j = 0; j < 6; j++) {
                scores[j] = scanner.nextInt();
            }
            candidates[i] = new Candidate(name, scores);
        }
        
        mergeSort(candidates, 0, n - 1);
        
        System.out.println("Shortlist:");
        for (int i = 0; i < n; i++) {
            Candidate c = candidates[i];
            String sign = c.bestClimb > 0 ? "+" : "";
            System.out.println((i + 1) + ". " + c.name + " " + c.total + " | best climb " + sign + c.bestClimb + " (test " + c.climbStart + " to " + c.climbEnd + ")");
        }
        
        while (scanner.hasNext()) {
            String queryType = scanner.next();
            int val = scanner.nextInt();
            
            if (queryType.equals("CUTOFF")) {
                int low = 0;
                int high = n - 1;
                int ans = n;
                
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    if (candidates[mid].total < val) {
                        ans = mid;
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
                System.out.println("CUTOFF " + val + ": " + ans + " candidates");
                
            } else if (queryType.equals("PAIR")) {
                int left = 0;
                int right = n - 1;
                boolean found = false;
                
                while (left < right) {
                    int sum = candidates[left].total + candidates[right].total;
                    if (sum == val) {
                        System.out.println("PAIR " + val + ": " + candidates[left].name + " + " + candidates[right].name);
                        found = true;
                        break;
                    } else if (sum > val) {
                        left++;
                    } else {
                        right--;
                    }
                }
                
                if (!found) {
                    System.out.println("PAIR " + val + ": no pair");
                }
            }
        }
        scanner.close();
    }
}
