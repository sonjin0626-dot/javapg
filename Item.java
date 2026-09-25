public class Item { // Itemという名前のクラス（プログラムのまとまり）を作ります。
    public static void main(String[] args) { // プログラムを開始するための特別なメソッド（処理の入口）です。
        String title = "Javaの勉強をする"; // String型（文字列を入れる型）の変数titleに文章を入れます。
        String html = "<li>" + title + "</li>"; // +で文字列をつなぎ、1行分のHTML文字列を作ります。
        System.out.println(html); // 作った文字列をターミナル（文字を表示する画面）に出力します
        boolean done = false; // boolean型（trueかfalseの2種類しか入らない型）の変数doneにfalseを入れます。
        System.out.println(done); // 変数doneの中身をターミナルに出力します。
        int count = 3;
        System.out.println("いま" + count + "件");
    } // mainメソッドの終わりです。
} // Itemクラスの終わりです。
