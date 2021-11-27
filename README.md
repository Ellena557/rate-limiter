paradoxProperties in Compile ++= Map(
  "include.files.base_dir" -> s"${(sourceDirectory in Test).value}/paradox"
)

# Rate limiter (Sliding Window)

* Реализация в директории src/main/java/window/slide
* Тесты в директории src/test/java/window/slide
* Описание работы алгоритма: [description.md]($files$/description.md)
* Доказательство корректности алгоритма: files/proof.md
* Описание архитектуры для распределенного Rate Limiter: files/distributed-architecture.md

