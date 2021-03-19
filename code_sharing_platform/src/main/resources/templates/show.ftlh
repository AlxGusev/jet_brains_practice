<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <title>Code</title>
</head>
<body>

<pre id="code_snippet"><code>${code.getCode()}</code></pre>
<br>
<#if code.hasViewLimit>
    <span id="views_restriction">${code.getViews()} more views allowed</span>
</#if>
<br>
<#if code.hasTimeLimit>
    <span id="time_restriction">The code will be available for ${code.getTimeLeft()} seconds</span>
</#if>
<br>
<span id="load_date"><code>${code.getDate()}</code></span>


</body>
</html>