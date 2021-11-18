package dev.stratospheric.todoapp.cdk;

import dev.stratospheric.cdk.ApplicationEnvironment;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.certificatemanager.DnsValidatedCertificate;
import software.amazon.awscdk.services.route53.HostedZone;
import software.amazon.awscdk.services.route53.HostedZoneProviderProps;
import software.amazon.awscdk.services.route53.IHostedZone;

public class CertificateStack extends Stack {

  public CertificateStack(
    final Construct scope,
    final String id,
    final Environment awsEnvironment,
    final ApplicationEnvironment applicationEnvironment,
    final String applicationDomain,
    final String hostedZoneDomain) {
    super(scope, id, StackProps.builder()
      .stackName(applicationEnvironment.prefix("Certificate"))
      .env(awsEnvironment).build());

    IHostedZone hostedZone = HostedZone.fromLookup(this, "HostedZone", HostedZoneProviderProps.builder()
      .domainName(hostedZoneDomain)
      .build());

    DnsValidatedCertificate websiteCertificate = DnsValidatedCertificate.Builder.create(this, "WebsiteCertificate")
      .hostedZone(hostedZone)
      .region(awsEnvironment.getRegion())
      .domainName(applicationDomain)
      .build();
  }
}
