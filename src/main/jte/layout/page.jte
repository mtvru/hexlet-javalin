@import gg.jte.Content
@param Content content

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Хекслет</title>
</head>
<body>
@template.layout.header()
${content}
@template.layout.footer()
</body>
</html>
