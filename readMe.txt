

   @Id
   @GeneratedValue(generator = "UUID") //UUID bizim generate et dediğimiz zaman o zmaan timespand i alır ve bir hascode üretir ve bu hascode bizim uuid miz olur
   @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator") //uuid strategy sını belirledik.

   //uuid aslında unique bir id miz olsun ve tahmin edilemesin


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id",nullable = false)
    private Customer customer;

   //fetch = FetchType.LAZY lazy burada loop olusmasının onune geciyor orneğin account customerı cekecek sonra customer account u cekecek dönngü olusmasını engellıyor. Eager tam tersi.

   //cascade = CascadeType.ALL bu ilişkiye ait herhangi bir entity de yapılacak herhangi bir işlemde (insert delete vs) eğer account  ait bir customer nesnesi gunceşllendıyse customer tablosundada git o işi yap


//double yerine bigdecimal kullan