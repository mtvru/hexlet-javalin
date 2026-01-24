@import io.javalin.validation.ValidationError
@import org.example.hexlet.dto.BasePage
@import gg.jte.Content
@import java.util.List
@param Content content
@param BasePage page = null

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Hexlet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
@template.layout.header()
<br/>
@if(page != null && page.getFlash() != null)
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ${page.getFlash()}
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
@endif
@if(page != null && page.getErrors() != null)
    <div class="alert alert-danger alert-dismissible" role="alert">
        <ul>
            @for(List<ValidationError<Object>> validator : page.getErrors().values())
                @for(ValidationError error : validator)
                    <li>${error.getMessage()}</li>
                @endfor
            @endfor
        </ul>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
@endif
<br/>
${content}
<br/>
@template.layout.footer()
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
</body>
</html>
