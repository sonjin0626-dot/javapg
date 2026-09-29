public class Items { // Itemsという名前のクラス（プログラムのまとまり）を定義します
    public static void main(String[] args) { // プログラムを開始する特別なメソッド（処理のまとまり）です
        String[] todos = { "牛乳を買う", "", "パンを買う", "掃除をする" }; // Todoの文字列3件を配列（複数のデータをまとめるもの）に入れます
        boolean[] done = { true, false, false, false };
        for (int i = 0; i < todos.length; i++) { // iを使って、配列の先頭から最後まで1件ずつ繰り返します

            if (!todos[i].isEmpty()) {
                String mark = done[i] ? "[済] " : "";
                System.out.println("<li>" + mark + todos[i] + "</li>");
            }
        } // for文の繰り返しを終わります
    } // mainメソッドを終わります
} // Itemsクラスを終わります
