const result = JSON.parse(document.getElementById("resultData").value);

if (result.isError) {
    document.getElementById("resultTable").innerText = "JSONの解析に失敗しました。フォーマットが不正な可能性があります。";

} else if (result.length > 0) {
    let tableHtml = `<table border="1"><thead><tr><th>日付</th><th>接触回数</th><th>リスクスコア</th><th>接触時間</th><th>最小db</th><th>最大db</th></tr></thead>`
    result.forEach(elm => {
        const size = elm.cl.length;
        let isFirst = true;
        elm.cl.forEach(info => {
            if (isFirst) {
                tableHtml += `<tbody><tr><td rowspan="${size}">${elm.d}</td><td rowspan="${size}">${elm.cn}</td><td rowspan="${size}">${elm.ss}</td>`
                isFirst = false;
            } else {
                tableHtml += `<tr>`
            }
            tableHtml += `<td>${info.cm}</td><td>${info.miad}</td><td>${info.maad}</td></tr>`
        });
    });
    tableHtml += `</tbody></table>`
    const table = document.createElement("div");
    table.insertAdjacentHTML('afterbegin', tableHtml)
    document.getElementById("resultTable").replaceWith(table);
}
