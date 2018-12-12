function quillEventHandler() {
    if (typeof quillNum !== "undefined"){
        if (quillNum > 0) {
            for (let i = 0; i < quillNum; i++) {
                let quill = quillInstances[i];
                let value = document.getElementById(quillTextAreas[i]);
                quill.on('text-change', function (delta, oldDelta, source) {
                    let contents = quill.getContents();
                    value.value = JSON.stringify(contents);
                })
            }
        }
    }
}

quillEventHandler();