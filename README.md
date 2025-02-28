# MemoryTest

## プログラムの目的
このプログラムは、PDFファイルを読み込み、その内容を`OutputStream`に書き込むことでメモリ使用量を計測することを目的としています。

## 使用ライブラリ
- **Apache PDFBox**: PDFファイルの読み込みとテキスト抽出に使用します。

## 主なクラスとメソッド
- **MemoryTest**: メインクラスで、メモリ使用量の計測を行います。
  - `main(String[] args)`: プログラムのエントリーポイント。試行回数を設定し、メモリ使用量を計測します。
  - `readPdfAndWriteToStream(String pdfFilePath, OutputStream out)`: PDFファイルを読み込み、内容を指定された`OutputStream`に書き込みます。

## プログラムの流れ
1. **試行回数の設定**: `iterations`変数で試行回数を設定します。
2. **メモリ使用量のトラッキング開始**: `Runtime`クラスを使用してメモリ使用量をトラッキングします。
3. **OutputStreamのメモリ使用量を計測**:
   - `MockOutputStream`を使用してPDFファイルの内容をストリームに書き込みます。
   - メモリ使用量を計測して表示します。
4. **ByteArrayOutputStreamのメモリ使用量を計測**:
   - `ByteArrayOutputStream`を使用してPDFファイルの内容をストリームに書き込みます。
   - メモリ使用量を計測して表示します。
