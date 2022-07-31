const result = JSON.parse(document.getElementById("result-data").value);

if (result.isError) {
    document.getElementById("result-table").innerText = "JSONの解析に失敗しました。フォーマットが不正な可能性があります。";

} else if (result.length > 0) {
    let insertHtml = "";
    let i = 0;
    result.forEach(elm => {
        insertHtml += `<div class="result-element"><div class="collapsable-toggle">
            <a data-bs-toggle="collapse" data-bs-target="#clps${i}" aria-expanded="false" aria-controls="clps${i}">
            ${elm.d} 接触数: ${elm.cn} スコア: ${elm.ss}</a></div>`;
        insertHtml += `<div class="collapse" id="clps${i++}">
            <table><thead><th>No</th><th>接触時間(分)</th><th>最小db</th><th>最大db</th></tr></thead><tbody>`;
        let idx = 1;
        elm.cl.forEach(info => {
            insertHtml += `<tr><td>${idx++}</td><td>${info.cm}</td><td>${info.miad}</td><td>${info.maad}</td></tr>`;
        });
        insertHtml += `</tbody></table></div></div>`;
    });

    const table = document.createElement("div");
    table.id = "result-table"
    table.insertAdjacentHTML('afterbegin', insertHtml);
    document.getElementById("result-table").replaceWith(table);
}
