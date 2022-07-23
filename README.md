# COCOA JSON Parser

COCOAから取得したJSONデータ(exposure_data.json)を読み込み、解析するツール

### Spec
* SpringBoot 2.7.2
* Java 11

### How to boot
Java 11が使えれば、以下でビルドしてlocalhostで立ち上げられます。
```
# Get source
git clone https://github.com/chag-sh/cocoa-json-parser.git
cd cocoa-json-parser

# Build
./gradlew build

# Run
./gradlew bootRun

# Access to localhost on browser
localhost:8080
```

もしくは、以下URLにアクセスして試すこともできます(Herokuの無料枠なので制限あり)。  
https://cocoa-json-parser.herokuapp.com/
