# Endpointler

Swagger dökümanına ulaşmak için /document.html linki kullanılmalıdır.

### Kullanıcı giriş yapması

    /login -- POST

Kullanıcı giriş yapmak için yukarıdaki enpoint kullanılır. Giriş yapmak için aşağıda ki biçimde request body
gönderilmesi gerekmektedir.

    {
        "username": "example3",
        "password": "123456"
    }

Parametreler doğru verildiği takdir de aşağıdaki gibi 200 HTTP statü kodu ile cevap dönecektir.

    {
        "username": "example3",
        "role": "ROLE_USER",
        "token": "Bearer token"
    }

Tüm kullanıcılar için parola **123456** dır. 
Kullanılabilecek kullanıcı listesi ve rolleri listelenmiştir

    example1 -- ADMIN
    example2 -- USER
    example3 -- USER
    example4 -- USER
    example5 -- USER
    example6 -- USER

### Kullanıcı mesaj config

    /config -- POST

Kullanıcı mesaj config değiştirmek için yukarıdaki enpoint kullanılır.

    {
        "config": "{{phone}} {{date}} {{number}}",
        "lang": "TURKISH"
    }

{{phone}} {{date}} {{number}} alanları mutlaka girilmelidir. lang alanının değeri TURKISH veya ENGLISH olmalıdır. Bu
alanlar mesaj formatını belirtmektedir.

{{phone}} telefon numarasını, {{date}} tarihi, {{number}} arama sayısını temsil eder.

### Kullanıcıya telefon eklenmesi

NOT: Bu işlemi sadece **ADMIN** rolündeki **example1** kullanıcısı yapabilecektir.

    /phones -- POST

Kullanıcıya telefon ekleyebilmek için yukarıdaki enpoint kullanılmalıdır. Enpointe istek atarken aşağıdaki gibi bir Body
gönderilmesi gerekmektedir.

İstek atabilmek için Authorization header istek içinde gönderilmelidir. Format olarak ise Bearer token biçimde
olmalıdır.

    {
        "phone": "5555555555",
        "userId": 2
    }

Bir telefon sadece bir kullanıciya eklenebilir. Bir kullanıcının bir telefonu olabilir.

**NOT: example2 ve example3 kullanıcıları için sırasıyla 5554443322 ve 5554443321 numaraları sisteme eklendiği için bu
kullanıcılara numara eklenirken hata alınacaktır.**

### Kullanıcının arama yapması

    /phones/call -- POST

Kullanıcınin arama yapabilmesi için yukarıdaki enpoint kullanılmalıdır. Enpointe istek atarken aşağıdaki gibi bir Body
gönderilmesi gerekmektedir.

İstek atabilmek için Authorization header istek içinde gönderilmelidir. Format olarak ise Bearer token biçimde
olmalıdır.

    {
        "to": "5555555555"
    }

### Cevapsız çaprıların okundu olarak işaretlenmesi

    /phones -- PATCH

Kullanıcınin cevapsız çağrıları okundu olarak işaretlemesi için yukarıdaki enpoint kullanılmalıdır.

İstek atabilmek için Authorization header istek içinde gönderilmelidir. Format olarak ise Bearer token biçimde
olmalıdır.

### WebSocket Kullanımı

Websocket bağlantısı için /notification endpointi kullanılmalıdır.

İstek atabilmek için Authorization header istek içinde gönderilmelidir. Format olarak ise Bearer token biçimde
olmalıdır.

Kullanıcı bağlandığı an cevapsız çağrılar varsa kullanıcıya cevapsız çağrı bilgileri iletilecektir.

Eğer çağrı yapan kullanıcı da sisteme bağlı ise çağrı atılan numaranın artık müsait olduğu bilgisi gönderilecektir.

## Uygulamanın Çalıştırılması

Öncelikle docker image oluşturmak için aşağıdaki komut çalıştırılmalıdır.

    docker build -t orion .

Docker image oluşturulduktan sonra aşağıdaki komut çalıştırılmalıdır.

    docker run -dp 8080:8080 orion